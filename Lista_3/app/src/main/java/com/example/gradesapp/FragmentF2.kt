package com.example.gradesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginapp.databinding.FragmentF2Binding

class FragmentF2 : Fragment() {

    private lateinit var binding: FragmentF2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentF2Binding.inflate(inflater)

        binding.recyclerView.apply {
            adapter = GradesAdapter(ModelData.wordList)
            layoutManager = LinearLayoutManager(requireContext())
        }

        return binding.root
    }
}