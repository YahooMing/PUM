package com.example.list_4

import androidx.compose.runtime.*

@Composable
fun QuizApp(questions: List<Question>) {
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var selectedAnswer by remember { mutableStateOf<Int?>(null) }
    var score by remember { mutableStateOf(0) }
    var isQuizFinished by remember { mutableStateOf(false) }

    if (isQuizFinished) {
        QuizResultScreen(score = score, totalQuestions = questions.size)
    } else {
        val currentQuestion = questions[currentQuestionIndex]

        QuizScreen(
            question = currentQuestion.text,
            progress = (currentQuestionIndex + 1) / questions.size.toFloat(),
            answers = currentQuestion.answers,
            selectedAnswer = selectedAnswer,
            onAnswerSelected = { index -> selectedAnswer = index },
            onNextClicked = {
                if (selectedAnswer == currentQuestion.correctAnswerIndex) {
                    score++
                }
                selectedAnswer = null
                if (currentQuestionIndex < questions.size - 1) {
                    currentQuestionIndex++
                } else {
                    isQuizFinished = true
                }
            }
        )
    }
}
