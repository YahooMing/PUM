package com.example.lista2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController

class FragmentD : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_d, container, false)

        val tekst: TextView = view.findViewById(R.id.d_usrname)
        val button: Button = view.findViewById(R.id.d_logout)

        val username = arguments?.getString("username") ?: "użytkowniku"
        tekst.text = "Witaj, $username!"

        button.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentD_to_fragmentA2)
        }

        return view
    }
}