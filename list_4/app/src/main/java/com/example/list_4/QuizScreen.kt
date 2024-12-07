package com.example.list_4

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun QuizScreen(
    question: String,
    progress: Float,
    answers: List<String>,
    selectedAnswer: Int?,
    onAnswerSelected: (Int) -> Unit,
    onNextClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Nagłówek z numerem pytania i pasek postępu
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Pytanie ${(progress * 10).toInt()}/10",
                style = MaterialTheme.typography.headlineSmall, // Zamiast h6
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Treść pytania
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Text(
                text = question,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge // Zamiast body1
            )
        }

        // Lista odpowiedzi (RadioButtons)
        Column {
            answers.forEachIndexed { index, answer ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onAnswerSelected(index) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedAnswer == index,
                        onClick = { onAnswerSelected(index) }
                    )
                    Text(
                        text = answer,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }

        // Przycisk "Następne"
        Button(
            onClick = onNextClicked,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Następne")
        }
    }
}
