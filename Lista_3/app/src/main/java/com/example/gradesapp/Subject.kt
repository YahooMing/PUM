package com.example.gradesapp

data class Subject(
    val name: String
)
object Subjects {
    val subjectsList = mutableListOf(
        Subject("matematyka"),
        Subject("PUM"),
        Subject("fizyka"),
        Subject("elektronika"),
        Subject("algorytmy")
    )
}