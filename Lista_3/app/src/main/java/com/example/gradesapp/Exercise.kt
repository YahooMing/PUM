package com.example.gradesapp

data class Exercise(
    val content: String,
    val points: Int
) {
    companion object {
        fun generateExercise(num: Int): MutableList<Exercise> {
            val loremWords = listOf(
                "Lorem", "ipsum", "dolor", "sit", "amet", "consectetur",
                "adipiscing", "elit", "sed", "do", "eiusmod", "tempor",
                "incididunt", "ut", "labore", "et", "dolore", "magna", "aliqua"
            )
            val exercises = mutableListOf<Exercise>()
            for (i in 0 until num) {
                val contentList = mutableListOf<String>()
                val randLength = (10..50).random()
                for (j in 0 until randLength) {
                    val randWord = loremWords.random() // Losowy wybór słowa z listy
                    contentList.add(randWord)
                }
                val finalContent = contentList.joinToString(" ")
                val finalPoints = (0..10).random()
                exercises.add(Exercise(finalContent, finalPoints))
            }
            return exercises
        }
    }
}
