package com.example.diplomaproject.mainpage

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.diplomaproject.R
import com.example.diplomaproject.core.Constants
import com.example.diplomaproject.core.repository.SharedPreferencesRepo
import com.example.diplomaproject.core.state.ToDoListState
import com.example.diplomaproject.databinding.FragmentToDoListBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ToDoListFragment: Fragment(R.layout.fragment_to_do_list) {

    @Inject lateinit var sharedPreferencesRepo: SharedPreferencesRepo
    private val viewBinding: FragmentToDoListBinding by viewBinding()
    private val viewModel: ToDoListViewModel by viewModels()

    private val toDoListAdapter by lazy(LazyThreadSafetyMode.NONE) {
        ToDoListAdapter()
    }

    private lateinit var date: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        date = requireArguments().getString(Constants.TO_DO_LIST_DATE).toString()

        initActions()
        initObservers()

        viewModel.getToDoList(
            token = sharedPreferencesRepo.getUserRefreshToken(),
            time = date
        )
    }

    private fun initActions() = with(viewBinding) {
        toDoListRecycler.adapter = toDoListAdapter
        toDoListRecycler.layoutManager = LinearLayoutManager(context)

        textViewDate.text = date

        fab.setOnClickListener {
            val bottomSheet = ToDoBottomSheetDialog()
            bottomSheet.arguments = Bundle().apply {
                putString(Constants.TO_DO_LIST_DATE, date)
            }
            bottomSheet.show(parentFragmentManager, "MyBottomSheetTag")
        }

        refreshLayoutToDoList.setOnRefreshListener {
            viewModel.getToDoList(
                token = sharedPreferencesRepo.getUserRefreshToken(),
                time = date
            )
        }
    }

    private fun initObservers() {
        viewModel.toDoListState.observe(viewLifecycleOwner, ::handleToDoListState)
    }

    private fun handleToDoListState(state: ToDoListState) = with(viewBinding) {
        when (state) {
            is ToDoListState.Failed -> {
                Log.e("ToDoListFragmentTag", state.message)
            }
            is ToDoListState.Loading -> {}
            is ToDoListState.Success -> {
                val tasks = state.result
                toDoListAdapter.submitList(tasks)
                refreshLayoutToDoList.isRefreshing = false
            }
        }
    }

}