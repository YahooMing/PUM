package com.example.lista1

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import android.view.View
import androidx.cardview.widget.CardView


data class ModelDanych(
    val pytanko: String,
    val odpowiedz: List<String>,
    val bingo: Int
)
class MainActivity : AppCompatActivity() {

    private lateinit var pytanie: TextView
    private lateinit var nr_pytania: TextView
    private lateinit var odp1: RadioButton
    private lateinit var odp2: RadioButton
    private lateinit var odp3: RadioButton
    private lateinit var odp4: RadioButton
    private lateinit var radio_group: RadioGroup
    private lateinit var przycisk: Button
    private lateinit var progress: ProgressBar
    private lateinit var gratulacje: TextView
    private lateinit var kartka : CardView

    private var lista_pytan: ArrayList<ModelDanych> = ArrayList()
    private var nr_obecnego_pytania = 0
    private var wynik = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) //R to klasa generowana automatycznie przez android, resources

        pytanie = findViewById(R.id.pytanie)
        nr_pytania = findViewById(R.id.numer)
        odp1 = findViewById(R.id.odp1)
        odp2 = findViewById(R.id.odp2)
        odp3 = findViewById(R.id.odp3)
        odp4 = findViewById(R.id.odp4)
        radio_group = findViewById(R.id.gruparadio)
        przycisk = findViewById(R.id.przycisk)
        progress = findViewById(R.id.progres)
        gratulacje = findViewById(R.id.gratulejszyn)

        lista_pytan.add(
            ModelDanych(
                "Jaką właściwość ciała określa stosunek masy do objętości?",
                listOf("Prędkość", "Energia kinetyczna", "Gęstość", "Temperatura"),
                2
            )
        )
        lista_pytan.add(
            ModelDanych(
                "Jaka jest prędkość światła?",
                listOf("300,000 km/s", "150,000 km/s", "1,000 km/s", "299,792 km/s"),
                3
            )
        )
        lista_pytan.add(
            ModelDanych(
                "Kiedy miała miejsce bitwa pod Grunwaldem?",
                listOf("1410", "1450", "1500", "1350"),
                0
            )
        )

        lista_pytan.add(
            ModelDanych(
                "Jakie jest największe jezioro w Polsce?",
                listOf("Jezioro Białe", "Jezioro Śniardwy", "Jezioro Mamry", "Jezioro Niegocin"),
                1
            )
        )

        lista_pytan.add(
            ModelDanych(
                "Kto napisał 'Pana Tadeusza'?",
                listOf("Adam Mickiewicz", "Juliusz Słowacki", "Henryk Sienkiewicz", "Bolesław Prus"),
                0
            )
        )

        lista_pytan.add(
            ModelDanych(
                "Jak nazywa się proces oddychania roślin?",
                listOf("Fotosynteza", "Fermentacja", "Oddychanie komórkowe", "Transpiracja"),
                0
            )
        )

        lista_pytan.add(
            ModelDanych(
                "Jakie miasto było gospodarzem Letnich Igrzysk Olimpijskich w 2008 roku?",
                listOf("Ateny", "Pekin", "Londyn", "Rio de Janeiro"),
                1
            )
        )

        lista_pytan.add(
            ModelDanych(
                "Kto namalował 'Mona Lisę'?",
                listOf("Vincent van Gogh", "Pablo Picasso", "Leonardo da Vinci", "Claude Monet"),
                2
            )
        )

        lista_pytan.add(
            ModelDanych(
                "Jakie jest główne zadanie procesora w komputerze?",
                listOf("Przechowywanie danych", "Wykonywanie obliczeń", "Wyświetlanie obrazu", "Zasilanie urządzeń"),
                1
            )
        )

        lista_pytan.add(
            ModelDanych(
                "Jaki gatunek literacki reprezentuje 'Zbrodnia i kara' Fiodora Dostojewskiego?",
                listOf("Powieść kryminalna", "Powieść psychologiczna", "Dramat", "Esej"),
                1
            )
        )

        displayQuestion()

        przycisk.setOnClickListener {
            checkAnswer()
            nr_obecnego_pytania++
            if (nr_obecnego_pytania < lista_pytan.size) {
                displayQuestion()
            } else {
                endQuiz()
            }
        }
    }

    private fun displayQuestion() {
        val currentQuestion = lista_pytan[nr_obecnego_pytania]

        pytanie.text = currentQuestion.pytanko
        odp1.text = currentQuestion.odpowiedz[0]
        odp2.text = currentQuestion.odpowiedz[1]
        odp3.text = currentQuestion.odpowiedz[2]
        odp4.text = currentQuestion.odpowiedz[3]

        nr_pytania.text = "Pytanie ${nr_obecnego_pytania + 1}/${lista_pytan.size}"

        radio_group.clearCheck()

        progress.progress = ((nr_obecnego_pytania + 1) * 100 / lista_pytan.size)
    }

    private fun checkAnswer() {
        val selectedOptionId = radio_group.checkedRadioButtonId
        val selectedAnswerIndex = when (selectedOptionId) {
            R.id.odp1 -> 0
            R.id.odp2 -> 1
            R.id.odp3 -> 2
            R.id.odp4 -> 3
            else -> -1 // Jakby nie było odpowiedzi
        }

        if (selectedAnswerIndex == lista_pytan[nr_obecnego_pytania].bingo) {
            wynik++
        }
    }

    private fun endQuiz() {
        pytanie.text = "Zakończono Quiz"
        radio_group.clearCheck()
        radio_group.visibility = View.GONE
        odp1.visibility = View.GONE
        odp2.visibility = View.GONE
        odp3.visibility = View.GONE
        odp4.visibility = View.GONE
        gratulacje.visibility = View.VISIBLE
        gratulacje.text = "Twój wynik to: $wynik/${lista_pytan.size}"
        przycisk.visibility = View.GONE
        nr_pytania.visibility = View.GONE
        progress.visibility = View.GONE
        pytanie.setBackgroundColor(Color.TRANSPARENT)
    }
}