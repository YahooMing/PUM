package com.example.lista2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.lista2.databinding.FragmentABinding
import com.example.lista2.databinding.FragmentBBinding

class FragmentB : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_b, container, false)

        val registerButton: Button = view.findViewById(R.id.b_register)
        registerButton.setOnClickListener {
            // Implementacja rejestracji
        }

        return view
    }

}