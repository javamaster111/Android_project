package com.example.diplomaproject.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.diplomaproject.R
import com.example.diplomaproject.databinding.FragmentResetBinding
import com.example.diplomaproject.databinding.FragmentStartBinding


class ResetFragment : Fragment() {

    private lateinit var binding: FragmentResetBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResetBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.buttonSendCode.setOnClickListener {
            findNavController().navigate(R.id.action_resetFragment_to_verificationFragment)
        }


    }

}