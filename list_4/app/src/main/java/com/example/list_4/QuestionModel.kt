package com.example.list_4

data class Question(
    val text: String,
    val answers: List<String>,
    val correctAnswerIndex: Int
)

fun generateQuestions(): List<Question> {
    return listOf(
        Question(
            text = "Jaką właściwość ciała określa stosunek masy do objętości?",
            answers = listOf("Prędkość", "Energia kinetyczna", "Gęstość", "Temperatura"),
            correctAnswerIndex = 2
        ),
        Question(
            text = "Jaka jest prędkość światła?",
            answers = listOf("300,000 km/s", "150,000 km/s", "1,000 km/s", "299,792 km/s"),
            correctAnswerIndex = 3
        ),
        Question(
            text = "Kiedy miała miejsce bitwa pod Grunwaldem?",
            answers = listOf("1410", "1450", "1500", "1350"),
            correctAnswerIndex = 0
        ),
        Question(
            text = "Jakie jest największe jezioro w Polsce?",
            answers = listOf("Jezioro Białe", "Jezioro Śniardwy", "Jezioro Mamry", "Jezioro Niegocin"),
            correctAnswerIndex = 1
        ),
        Question(
            text = "Kto napisał 'Pana Tadeusza'?",
            answers = listOf("Adam Mickiewicz", "Juliusz Słowacki", "Henryk Sienkiewicz", "Bolesław Prus"),
            correctAnswerIndex = 0
        ),
        Question(
            text = "Jak nazywa się proces oddychania roślin?",
            answers = listOf("Fotosynteza", "Fermentacja", "Oddychanie komórkowe", "Transpiracja"),
            correctAnswerIndex = 0
        ),
        Question(
            text = "Jakie miasto było gospodarzem Letnich Igrzysk Olimpijskich w 2008 roku?",
            answers = listOf("Ateny", "Pekin", "Londyn", "Rio de Janeiro"),
            correctAnswerIndex = 1
        ),
        Question(
            text = "Kto namalował 'Mona Lisę'?",
            answers = listOf("Vincent van Gogh", "Pablo Picasso", "Leonardo da Vinci", "Claude Monet"),
            correctAnswerIndex = 2
        ),
        Question(
            text = "Jakie jest główne zadanie procesora w komputerze?",
            answers = listOf("Przechowywanie danych", "Wykonywanie obliczeń", "Wyświetlanie obrazu", "Zasilanie urządzeń"),
            correctAnswerIndex = 1
        ),
        Question(
            text = "Jaki gatunek literacki reprezentuje 'Zbrodnia i kara' Fiodora Dostojewskiego?",
            answers = listOf("Powieść kryminalna", "Powieść psychologiczna", "Dramat", "Esej"),
            correctAnswerIndex = 1
        )

    )
}