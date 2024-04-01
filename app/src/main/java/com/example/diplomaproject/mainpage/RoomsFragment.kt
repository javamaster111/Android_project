package com.example.diplomaproject.mainpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.diplomaproject.R
import com.example.diplomaproject.databinding.FragmentResetBinding
import com.example.diplomaproject.databinding.FragmentRoomsBinding

class RoomsFragment : Fragment() {

    private lateinit var binding: FragmentRoomsBinding

    private val continentDialog = BottomSheetRoomFragment(true)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRoomsBinding.inflate(layoutInflater)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.floatingActionButton.setOnClickListener{
            continentDialog.show(childFragmentManager,"BottomSheetFragment")
        }

        binding.cardviewButton.setOnClickListener {

        }

//        binding.cardviewButton.setOnClickListener {
//            childFragmentManager.beginTransaction().apply {
//
//            }
//        }

    }
}