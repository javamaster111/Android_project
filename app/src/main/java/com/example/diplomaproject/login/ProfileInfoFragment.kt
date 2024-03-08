package com.example.diplomaproject.login

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.diplomaproject.R
import com.example.diplomaproject.databinding.FragmentProfileInfoBinding


class ProfileInfoFragment : Fragment() {
    private lateinit var binding: FragmentProfileInfoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpDropDownMenu()

        binding.buttonSave.setOnClickListener {
            findNavController().navigate(R.id.action_profileInfoFragment_to_homeActivity)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileInfoBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun setUpDropDownMenu() {
        binding.dropdownContainer.isVisible = true
        binding.dropdownContainerMonth.isVisible =  true
        binding.dropdownContainerYear.isVisible = true
        if (binding.dropdownMenu.isVisible==true) {
            val items = listOf("1", "2", "3", "4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20",
                "21","22","23","24","25","26","27","28","29","30","31",)

            binding.dropdownMenu.setUpDropdownMenu(items)
        }
        if (binding.dropdownContainerMonth.isVisible ==true){
            val items  = listOf("january","february","march","april","may","june","july",
                "august","september","october","november","december")
            binding.dropdownMenuMonth.setUpDropdownMenu(items)
        }
        if (binding.dropdownContainerYear.isVisible == true){
            val items = listOf("1990","1991","1992","1993","1994","1995","1996","1997","1998","1999","2000",
                "2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013")
            binding.dropdownMenuYear.setUpDropdownMenu(items)

        }
        val itemCountry = listOf("Kazakhstan","Turkey")
        binding.dropdownMenuCountry.setUpDropdownMenu(itemCountry)

        binding.layoutt.setOnClickListener{
            hideKeyboard()
        }
    }
}
fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}
fun AutoCompleteTextView.setUpDropdownMenu(itemsList: List<String>) {
    val adapter = ArrayAdapter(this.context, R.layout.item_text, itemsList)
    this.setAdapter(adapter)
}