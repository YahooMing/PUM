package com.example.gradesapp

data class ExerciseList(
    val exercises: MutableList<Exercise> = mutableListOf(),
    val subject: Subject,
    val grade: Double
) {

    companion object {
        fun generateExerciseList(): ExerciseList {
            val exerciseCount = (1..10).random()
            val finalExercises = Exercise.generateExercise(exerciseCount)
            val subjectIdx = (0..4).random()
            val finalSubject = Subjects.subjectsList[subjectIdx]
            val finalGrade = ((6..10).random().toDouble()) / 2
            return ExerciseList(finalExercises, finalSubject, finalGrade)
        }

        object ExerciseListProvider {
            val allExerciseLists: MutableList<ExerciseList> by lazy {
                MutableList(20) { ExerciseList.generateExerciseList() }
            }
        }
    }
}