package com.example.gradesapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginapp.R
import com.example.loginapp.databinding.FragmentF1Binding

class FragmentF1 : Fragment() {

    private lateinit var binding: FragmentF1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentF1Binding.inflate(inflater) //wiązanko z xml

        binding.recyclerView.apply { //konfiguracja RecyclerView
            //ustawienie adaptera dla recyclera przekazując liste ćwiczeń, żeby obsłużyć kliknięcia na elementy listy
            adapter = WordListAdapter(ExerciseList.Companion.ExerciseListProvider.allExerciseLists) { clickedItem ->
                //Pobiera indeks klikniętego elementu
                val currentIndex = ExerciseList.Companion.ExerciseListProvider.allExerciseLists.indexOf(clickedItem)
                //oblicza liczbe ćwiczeń z tym samym przedmiotem do bieżącego indeksu
                val listCount = ExerciseList.Companion.ExerciseListProvider.allExerciseLists
                    .subList(0, currentIndex+1)
                    .count { it.subject == clickedItem.subject  }
                //tworzy podliste ćwiczeń do bieżącego indeksu
                val subList = ExerciseList.Companion.ExerciseListProvider.allExerciseLists.subList(0, currentIndex + 1)
                //oblicza liczbe ćwiczeń z tym samym przedmiotem w podliście
                val listCount2 = subList.count { it.subject == clickedItem.subject }
                //pobiera nazwe przedmiotu klikniętego elementu
                val subj = clickedItem.subject.name
                //tworzy akcje przekazującą nazwe przedmiotu i liczbe ćwiczeń do fragmentu 3
                val action = FragmentF1Directions.actionFragmentF1ToFragmentF3(subj, listCount2)
                //nawiguje do fragmentu 3
                Navigation.findNavController(requireView()).navigate(action)

            }

            layoutManager = LinearLayoutManager(requireContext())
        }

        return binding.root
    }
}