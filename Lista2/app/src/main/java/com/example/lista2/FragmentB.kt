package com.example.lista2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.lista2.databinding.FragmentABinding
import com.example.lista2.databinding.FragmentBBinding
import androidx.navigation.fragment.findNavController

class FragmentB : Fragment() {

    private lateinit var userManager: UserManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_b, container, false)

        userManager = UserManager(requireContext())

        val registerButton: Button = view.findViewById(R.id.b_register)
        val login: EditText = view.findViewById(R.id.b_login)
        val pass: EditText = view.findViewById(R.id.b_haslo)
        val rpass: EditText = view.findViewById(R.id.b_phaslo)
        registerButton.setOnClickListener {
            val s_login = login.text.toString()
            val s_pass = pass.text.toString()
            val s_rpass = rpass.text.toString()

            if(s_pass == s_rpass){
                val adding = userManager.addUser(s_login, s_pass)
                if(adding){
                    Toast.makeText(requireContext(), "Dodano nowego użytkownika", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_fragmentB_to_fragmentC)
                }else{
                    Toast.makeText(requireContext(), "Ten użytkownik już istnieje", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(), "Hasła nie są takie same", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

}