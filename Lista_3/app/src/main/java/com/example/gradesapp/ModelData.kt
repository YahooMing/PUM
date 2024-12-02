package com.example.gradesapp
import kotlin.math.round

object ModelData {
    //lista trzech rzeczy, nazwa przedmiotu, średnia, liczba ćwiczeń
    val wordList: List<Triple<String, Double, Int>> = calculateAverages()
    //liczonko średniej
    private fun calculateAverages(): List<Triple<String, Double, Int>> {
        return ExerciseList.Companion.ExerciseListProvider.allExerciseLists
            .groupBy { it.subject.name } //grupowanie przez nazwe przedmiotu
            .map { (subjectName, exercises) -> //przekształcanie grupy w te liste potrójną
                val totalPoints = exercises.sumOf { it.grade } //suma ocenek
                val average = if (exercises.isNotEmpty()) { //średnia liczona i zaokrąglana do dwóch miejsc po przecinku
                    round((totalPoints / exercises.size) * 100) / 100
                } else {
                    0.00
                }
                val exerciseCount = exercises.size //jak dużo jest zadań
                Triple(subjectName, average, exerciseCount) //tu eleganckie tworzenie trójek
            }
    }
}
