package com.example.lista6

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlin.random.Random

// Wyposażamy main activity w rzeczy które nas interesują, czyli Liste zadań oraz ocenki
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExerciseList.genExList(); //lista zadań
            Grades.sumEx(); //ocenki
            Navigation(Modifier)
        }
    }
}

//Zadanka
data class Exercise(
    val content: String,
    val points: Int
)

//Przedmiot
data class Subject (
    val name: String,
)

//Ocenki
data class Grades (
    val subject: Subject,
    val average: Double,
    val appearances: Int
) {
    companion object { //deklaracja członków klasy bez bez potrzeby tworzenia instancji tej klasy
        fun sumEx(): MutableList<Grades> {
            for (i in 0..4) {
                var sum = 0.0;
                var counter = 0;
                for(k in 0..19) {
                    if(exList[k].subject.name == Subjects[i].name) {
                        sum += exList[k].grade;
                        counter++;
                    }
                }
                var avg = 0.0;
                if(counter > 0) {
                    avg = sum / counter;
                }
                sumGrades.add(Grades(Subjects[i], avg, counter))
            }
            sumGrades.removeAll { it.appearances == 0 }
            return sumGrades
        }
    }
}

data class ExerciseList (
    val exercises: MutableList<Exercise>,
    val subject: Subject,
    val grade: Float,
) {
    companion object {
        fun genExList() {
            for (i in 1..20) {
                val ex = MutableList(Random.nextInt(1,10)) {
                    Exercise(
                        content = "Lorem Ipsum jest tekstem stosowanym jako przykładowy wypełniacz w przemyśle poligraficznym. Został po raz pierwszy użyty w XV w. przez nieznanego drukarza do wypełnienia tekstem próbnej książki. Pięć wieków później zaczął być używany przemyśle elektronicznym, pozostając praktycznie niezmienionym. Spopularyzował się w latach 60. XX w. wraz z publikacją arkuszy Letrasetu, zawierających fragmenty Lorem Ipsum, a ostatnio z zawierającym różne wersje Lorem Ipsum oprogramowaniem przeznaczonym do realizacji druków na komputerach osobistych, jak Aldus PageMaker",
                        points = Random.nextInt(1,10)
                    )
                }
                exList.add(
                    ExerciseList(
                        exercises = ex,
                        subject = Subjects[Random.nextInt(0,Subjects.size)],
                        grade = (Random.nextInt(6, 11)).toFloat()/2
                    )
                )
            }
        }
    }
}

val Subjects = mutableListOf(
    Subject("Matematyka"),
    Subject("PUM"),
    Subject("Fizyka"),
    Subject("Elektronika"),
    Subject("Algorytmy")
)

sealed class Screens(val route: String) {
    data object E1 : Screens ("e1")
    data object E2 : Screens ("e2")
    data object E3 : Screens ("e3")
}

sealed class BottomBar(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object E1: BottomBar(Screens.E1.route, "Listy zadań", Icons.Default.Home)
    data object E2: BottomBar(Screens.E2.route, "Ocenki", Icons.Default.Home)
}

val exList = mutableListOf<ExerciseList>()
val sumGrades = mutableListOf<Grades>()

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(modifier: Modifier = Modifier){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomMenu(navController = navController)},
        content = { NavGraph(navController = navController, modifier = modifier) }
    )
}

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier){
    NavHost(
        navController = navController,
        startDestination = Screens.E1.route,
    ) {
        composable(route = Screens.E1.route){ E1(navController) }
        composable(route = Screens.E2.route){ E2() }
        composable(
            route = "${Screens.E3.route}/{index}",
            arguments = listOf(navArgument("index") {type=NavType.IntType})
        )
        {backStackEntry ->
            val index = backStackEntry.arguments!!.getInt("index");
            val exList = exList.getOrNull(index ?: -1)
            if(exList != null) {
                E3(index);
            }
            else {
                Text (text = "nie ma listy? dziwne co? jakiś błąd")
            }
        }
    }
}

@Composable
fun BottomMenu(navController: NavHostController) {
    val screens = listOf(
        BottomBar.E1, BottomBar.E2
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar{
        screens.forEach{screen ->
            NavigationBarItem(
                label = { Text(text = screen.title)},
                icon = {Icon(imageVector = screen.icon, contentDescription = "icon")},
                selected = currentDestination?.hierarchy?.any {it.route == screen.route} == true,
                onClick = {navController.navigate(screen.route)}
            )
        }
    }
}

@Composable
fun E1(navController: NavHostController) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Moje listy zadań",
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp, 40.dp),
            fontSize = 25.sp,
            textAlign = TextAlign.Center)
        LazyColumn(
            modifier = Modifier
                .padding(bottom = 100.dp)
        ) {
            items(exList.size) {
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .background(color = Color.hsl(60F, 1.0F, 0.94F))
                        .clickable { navController.navigate("${Screens.E3.route}/$it") }
                        .padding(30.dp),
                ) {
                    var count = 0
                    for (i in 0..(exList.size-1)) {
                        if (exList[it].subject == exList[i].subject) {
                            count++
                            if (i == it)
                                break
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(exList[it].subject.name, fontSize = 20.sp)
                        Text("Lista: " + count, fontSize = 20.sp)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Liczba zadań: " + exList[it].exercises.size.toString())
                        Text("Ocenka: " + exList[it].grade.toString())
                    }
                }
            }
        }
    }
}

@Composable
fun E2() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text("Moje ocenki",
            modifier = Modifier
                .fillMaxWidth()
                .padding(50.dp, 40.dp),
            fontSize = 30.sp,
            textAlign = TextAlign.Center)
        LazyColumn(
            modifier = Modifier
                .padding(bottom = 100.dp)
        ) {
            items(sumGrades.size) {
                Column (
                    modifier = Modifier.fillMaxWidth().padding(5.dp)
                        .background(color = Color.hsl(60F, 1.0F, 0.94F))
                        .padding(30.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(sumGrades[it].subject.name, fontSize = 20.sp)
                        Text("Ocenka: " + String.format("%.2f", sumGrades[it].average)
                            .toDouble(), fontSize = 20.sp)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Liczba list: " + sumGrades[it].appearances)
                    }
                }
            }
        }
    }
}

@Composable
fun E3(it: Int) {
    val arg = exList[it]
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(10.dp).fillMaxWidth()
    ) {
        var count = 0
        for (n in 0..(exList.size - 1)) {
            if (exList[n].subject == arg.subject) {
                count++
                if (exList[n] == arg)
                    break
            }
        }
        Text("${arg.subject.name} - List $count",
            modifier = Modifier
                .fillMaxWidth()
                .padding(50.dp, 40.dp),
            fontSize = 30.sp,
            textAlign = TextAlign.Center)
        LazyColumn(
            modifier = Modifier
                .padding(bottom = 100.dp)
        ) {
            items(arg.exercises.size) {
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .background(color = Color.hsl(60F, 1.0F, 0.94F))
                        .padding(30.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Zadanko ${it+1}", fontSize = 20.sp)
                        Text("pkt: ${arg.exercises[it].points}", fontSize = 20.sp)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(arg.exercises[it].content)
                    }
                }
            }
        }
    }
}
