package com.example.lista2

data class User(
    val username: String,
    val password: String
)

class UserManager {
    private val users = mutableListOf<User>()

    fun addUser(username: String, password: String): Boolean {
        if (users.any { it.username == username }) {
            return false // Użytkownik już istnieje
        }
        users.add(User(username, password))
        return true
    }

    fun getUser(username: String, password: String): Boolean {
        return users.any { it.username == username && it.password == password }
    }
}