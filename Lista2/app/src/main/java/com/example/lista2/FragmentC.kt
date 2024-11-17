package com.example.lista2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController

class FragmentC : Fragment() {

    private lateinit var userManager: UserManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_c, container, false)

        userManager = UserManager(requireContext())

        val loginButton: Button = view.findViewById(R.id.c_log)
        val registerButton: Button = view.findViewById(R.id.c_register)
        val login: EditText = view.findViewById(R.id.c_login)
        val password: EditText = view.findViewById(R.id.c_haslo)

        loginButton.setOnClickListener {
            val s_login = login.text.toString()
            val s_password = password.text.toString()

            val userExists = userManager.getUser(s_login, s_password)
            if (userExists) {
                Toast.makeText(requireContext(), "Logowanie udane", Toast.LENGTH_SHORT).show()
                val bundle = Bundle()
                bundle.putString("username", s_login)
                findNavController().navigate(R.id.action_fragmentC_to_fragmentD, bundle)
            } else {
                Toast.makeText(requireContext(), "Nieprawidłowy login lub hasło", Toast.LENGTH_SHORT).show()
            }
        }

        registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentC_to_fragmentB)
        }

        return view
    }
}