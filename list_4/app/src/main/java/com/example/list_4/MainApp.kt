package com.example.list_4

import androidx.compose.runtime.Composable

@Composable
fun MainApp() {
    val questions = generateQuestions()
    QuizApp(questions)
}