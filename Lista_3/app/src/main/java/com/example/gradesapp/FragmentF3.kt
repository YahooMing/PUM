package com.example.gradesapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginapp.databinding.FragmentF3Binding

class FragmentF3 : Fragment() {

    private lateinit var binding: FragmentF3Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentF3Binding.inflate(inflater)

        val subj = arguments?.getString("subject")
        val listCount = arguments?.getInt("listcount")?:0
        binding.subject.text = "$subj \n Lista $listCount"

        val allExerciseLists = ExerciseList.Companion.ExerciseListProvider.allExerciseLists
        val matchingList = allExerciseLists.filter { it.subject.name == subj }[listCount-1]
        val exercList = matchingList.exercises
        binding.recyclerView.apply {
            adapter = ExercisesAdapter(exercList)
            layoutManager = LinearLayoutManager(requireContext())
        }

        return binding.root
    }
}
