package com.example.lista6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Screen()
        }
    }
}

data class Student(
    val imie: String,
    val nazwisko: String,
    val idx: Int,
    val rok: Int,
    val ocenka: Int
)

object Data{
    val studenciaki: List<Student> = listOf(
        Student("Oskar","Chrostowski", 337384, 3, 5),
        Student("Kacper","Wiszniewski", 666666, 3, 2),
        Student("Mateusz", "Wójcicki", 123456, 3, 4),
        Student("Mateusz", "Wójcicki", 123456, 3, 4),
        Student("Mateusz", "Wójcicki", 123456, 3, 4),
        Student("Mateusz", "Wójcicki", 123456, 3, 4),
        Student("Mateusz", "Wójcicki", 123456, 3, 4),
        Student("Mateusz", "Wójcicki", 123456, 3, 4),
        Student("Mateusz", "Wójcicki", 123456, 3, 4),
        Student("Mateusz", "Wójcicki", 123456, 3, 4),
        Student("Mateusz", "Wójcicki", 123456, 3, 4),
        Student("Mateusz", "Wójcicki", 123456, 3, 4),
        Student("Mateusz", "Wójcicki", 123456, 3, 4),
        Student("Mateusz", "Wójcicki", 123456, 3, 4),
        Student("Mateusz", "Wójcicki", 123456, 3, 4),
        Student("Mateusz", "Wójcicki", 123456, 3, 4),
        Student("Mateusz", "Wójcicki", 123456, 3, 4),
        Student("Mateusz", "Wójcicki", 123456, 3, 4),
        Student("Mateusz", "Wójcicki", 123456, 3, 4),
        Student("Mateusz", "Wójcicki", 123456, 3, 4),
        Student("Mateusz", "Wójcicki", 123456, 3, 4),
        Student("Mateusz", "Wójcicki", 123456, 3, 4),
        Student("Mateusz", "Wójcicki", 123456, 3, 4),
        Student("Mateusz", "Wójcicki", 123456, 3, 4),
        Student("Mateusz", "Wójcicki", 123456, 3, 4),
        Student("Mateusz", "Wójcicki", 123456, 3, 4),
        Student("Mateusz", "Wójcicki", 123456, 3, 4),
        Student("Mateusz", "Wójcicki", 123456, 3, 4),

    )
}

class Studenciaki : ViewModel(){
    private var _lista = mutableStateListOf<Student>()
    val listeczka : List<Student>
        get() = _lista
    init{
        reinitialize()
    }

    fun reinitialize(){
        _lista.clear()
        _lista.addAll(Data.studenciaki)
        _lista.sortBy{ it.nazwisko }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onListDetails: (Int) -> Unit){
    val viewModel: Studenciaki = androidx.lifecycle.viewmodel.compose.viewModel()
    Column(modifier = Modifier.fillMaxSize()){
        Row(
            modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            horizontalArrangement = Arrangement.Center){
            Text("Studenciaki", fontSize = 24.sp)
        }
        Row(){
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                items(viewModel.listeczka.size){ index ->
                    Column(modifier = Modifier
                        .clickable{onListDetails(viewModel.listeczka[index].idx)}){
                        Row(){
                            Box(
                                modifier = Modifier
                                    .width(300.dp)
                                    .height(70.dp)
                                    .border(2.dp, Color.Black)
                                    .padding(8.dp)
                            ){
                                Text(
                                    viewModel.listeczka[index].imie+" "+viewModel.listeczka[index].nazwisko,
                                    fontSize = 30.sp, modifier = Modifier.padding(8.dp)
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Screen(){
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = Base.studenciaki.route){
        composable(route = Base.studenciaki.route){
            MainScreen { idx -> nav.navigate((Base.szczegol.createRoute(idx)))
            }
        }
        composable(
            route = Base.szczegol.route,
            arguments = listOf(navArgument("idx"){type = NavType.IntType})
        ){ backStackEntry ->
            val idx = backStackEntry.arguments?.getInt("idx") ?: -1
            ListDetails(idx = idx) { nav.popBackStack()
            }

        }
    }


}

sealed class Base(val route: String){
    data object studenciaki: Base("studenci")
    data object szczegol: Base("klik/{idx}"){
        fun createRoute(idx : Int) = "klik/$idx"
    }
}

@Composable
fun ListDetails(idx: Int, onMainPage: () -> Unit) {
    val student = Data.studenciaki.find { it.idx == idx }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (student != null) {
            Text("Szczegóły", fontSize = 30.sp, modifier = Modifier.padding(8.dp))
            Text("Numer Indeksu: ${student.idx}", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
            Text("Imie: ${student.imie}", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
            Text("Nazwisko: ${student.nazwisko}", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
            Text("Średnia ocen: ${student.ocenka}", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
            Text("Rok studiów: ${student.rok}", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
        } else {
            Text("ERROR 666", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
        }
    }
}