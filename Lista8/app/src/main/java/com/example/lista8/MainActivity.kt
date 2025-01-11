package com.example.lista8

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.*
import com.example.lista8.ui.theme.Lista8Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lista8Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNavigation()
                }
            }
        }
    }
}

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main_screen") {
        composable("main_screen") {
            MainScreen(navController)
        }
        composable("add_user_screen") {
            AddUserScreen(navController)
        }
        composable("edit_user_screen/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull()
            if (userId != null) {
                EditUserScreen(navController, userId)
            }
        }
    }
}

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val nazwaPrzedmiotu: String,
    val ocena: Int
)

@Dao
interface UserDao {
    @Query("SELECT * FROM user_table ORDER BY nazwaPrzedmiotu ASC")
    fun getUsers(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Query("DELETE FROM user_table WHERE id = :id")
    suspend fun delete(id: Int)

    @Update
    suspend fun update(user: User)
}

@Database(entities = [User::class], version = 2, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var Instance: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, UserDatabase::class.java, "user_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

class UserRepository(private val userDao: UserDao) {
    fun getUsers() = userDao.getUsers()
    suspend fun clear() = userDao.delete(0)
    suspend fun add(user: User) = userDao.insert(user)
    suspend fun update(user: User) = userDao.update(user)
    suspend fun delete(userId: Int) = userDao.delete(userId)
}

class UserViewModelFactory(val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(application) as T
    }
}

class UserViewModel(application: Application) : ViewModel() {

    private val repository: UserRepository
    private val _usersState = MutableStateFlow<List<User>>(emptyList())
    val usersState: StateFlow<List<User>>
        get() = _usersState

    init {
        val db = UserDatabase.getDatabase(application)
        val dao = db.userDao()
        repository = UserRepository(dao)

        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            repository.getUsers().collect { users ->
                _usersState.value = users
            }
        }
    }

    fun addUser(user: User) {
        viewModelScope.launch {
            repository.add(user)
            fetchUsers()
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            repository.update(user)
            fetchUsers()
        }
    }

    fun deleteUser(userId: Int) {
        viewModelScope.launch {
            repository.delete(userId)
            fetchUsers()
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    val viewModel: UserViewModel = viewModel(
        LocalViewModelStoreOwner.current!!,
        "UserViewModel",
        UserViewModelFactory(LocalContext.current.applicationContext as Application)
    )
    val users by viewModel.usersState.collectAsStateWithLifecycle()

    val averageGrade = if (users.isNotEmpty()) {
        users.map { it.ocena }.average()
    } else {
        0.0
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Wszystkie oceny", fontSize = 24.sp, modifier = Modifier.fillMaxWidth())
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(users.size) { index ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f), MaterialTheme.shapes.medium)
                        .padding(16.dp)
                        .clickable {
                            navController.navigate("edit_user_screen/${users[index].id}")
                        }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = users[index].nazwaPrzedmiotu,
                            fontSize = 20.sp
                        )
                        Text(
                            text = users[index].ocena.toString(),
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f), MaterialTheme.shapes.medium)
                .padding(16.dp)
        ) {
            Text(
                text = "Średnia ocen: %.2f".format(averageGrade),
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Button(
            onClick = { navController.navigate("add_user_screen") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text(text = "ADD")
        }
    }
}


@Composable
fun AddUserScreen(navController: NavHostController) {
    val nazwaPrzedmiotu = remember { mutableStateOf("") }
    val ocena = remember { mutableStateOf("") }

    val viewModel: UserViewModel = viewModel(
        LocalViewModelStoreOwner.current!!,
        "UserViewModel",
        UserViewModelFactory(LocalContext.current.applicationContext as Application)
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Dodaj wpis", fontSize = 24.sp)

        OutlinedTextField(
            value = nazwaPrzedmiotu.value,
            onValueChange = { nazwaPrzedmiotu.value = it },
            label = { Text("Nazwa przedmiotu") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = ocena.value,
            onValueChange = { ocena.value = it },
            label = { Text("Ocena") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        Button(
            onClick = {
                val ocenaInt = ocena.value.toIntOrNull()
                if (nazwaPrzedmiotu.value.isNotEmpty() && ocenaInt != null) {
                    val user = User(id = 0, nazwaPrzedmiotu = nazwaPrzedmiotu.value, ocena = ocenaInt)
                    viewModel.addUser(user)
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text(text = "DODAJ")
        }
    }
}

@Composable
fun EditUserScreen(navController: NavHostController, userId: Int) {
    val viewModel: UserViewModel = viewModel(
        LocalViewModelStoreOwner.current!!,
        "UserViewModel",
        UserViewModelFactory(LocalContext.current.applicationContext as Application)
    )

    val user = viewModel.usersState.collectAsStateWithLifecycle().value.find { it.id == userId }

    if (user != null) {
        val nazwaPrzedmiotu = remember { mutableStateOf(user.nazwaPrzedmiotu) }
        val ocena = remember { mutableStateOf(user.ocena.toString()) }

        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(text = "Edytuj wpis", fontSize = 24.sp)

            OutlinedTextField(
                value = nazwaPrzedmiotu.value,
                onValueChange = { nazwaPrzedmiotu.value = it },
                label = { Text("Nazwa przedmiotu") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            )

            OutlinedTextField(
                value = ocena.value,
                onValueChange = { ocena.value = it },
                label = { Text("Ocena") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            )

            Button(
                onClick = {
                    val ocenaInt = ocena.value.toIntOrNull()
                    if (nazwaPrzedmiotu.value.isNotEmpty() && ocenaInt != null) {
                        val updatedUser = user.copy(
                            nazwaPrzedmiotu = nazwaPrzedmiotu.value,
                            ocena = ocenaInt
                        )
                        viewModel.updateUser(updatedUser)
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) {
                Text(text = "ZATWIERDŹ")
            }

            Button(
                onClick = {
                    viewModel.deleteUser(user.id)
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) {
                Text(text = "USUŃ")
            }
        }
    } else {
        Text(
            text = "User not found",
            modifier = Modifier.fillMaxSize(),
            fontSize = 24.sp
        )
    }
}
