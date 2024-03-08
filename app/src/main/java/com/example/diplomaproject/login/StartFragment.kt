package com.example.diplomaproject.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.diplomaproject.R
import com.example.diplomaproject.databinding.FragmentAnimation1Binding
import com.example.diplomaproject.databinding.FragmentStartBinding
import com.google.android.material.textfield.TextInputEditText

class StartFragment : Fragment() {
    private lateinit var binding: FragmentStartBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogin.setOnClickListener{
            if (binding.fieldPassword.text.toString().isEmpty() || binding.fieldEmail.text.toString().isEmpty()){
                Toast.makeText(requireContext(),"fill all the fields", Toast.LENGTH_LONG).show()
            }
            else{
                findNavController().navigate(R.id.action_startFragment_to_homeActivity)
            }
        }
        binding.buttonForgot.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_resetFragment)
        }
        binding.buttonCreateAccount.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_registerFragment)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartBinding.inflate(layoutInflater)
        return binding.root
    }


}