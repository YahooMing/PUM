package com.example.lista2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.lista2.databinding.FragmentABinding
import androidx.navigation.fragment.findNavController

class FragmentA : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_a, container, false)

        val loginButton: Button = view.findViewById(R.id.a_login)
        val registerButton: Button = view.findViewById(R.id.a_register)

        loginButton.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentA2_to_fragmentC)
        }

        registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentA2_to_fragmentB)
        }

        return view


    }
}