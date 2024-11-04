package com.example.lista2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.lista2.databinding.FragmentABinding

class FragmentA : Fragment() {

    private lateinit var binding: FragmentABinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentABinding.inflate(inflater)

        binding.fabA.setOnClickListener {
            val action = FragmentADirections.actionFragmentA2ToFragmentB(5)
            Navigation.findNavController(requireView()).navigate(action)
        }

        return binding.root
    }
}