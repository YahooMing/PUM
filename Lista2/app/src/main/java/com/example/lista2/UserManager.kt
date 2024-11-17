package com.example.lista2
import android.content.Context
import android.content.SharedPreferences

class UserManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

    fun addUser(username: String, password: String): Boolean {
        if (sharedPreferences.contains(username)) {
            return false
        }
        val editor = sharedPreferences.edit()
        editor.putString(username, password)
        editor.apply()
        return true
    }

    fun getUser(username: String, password: String): Boolean {
        val storedPassword = sharedPreferences.getString(username, null)
        return storedPassword == password
    }
}