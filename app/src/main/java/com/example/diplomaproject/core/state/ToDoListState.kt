package com.example.diplomaproject.core.state

import com.example.diplomaproject.core.service.ToDoListItem

sealed class ToDoListState {
    data object Loading: ToDoListState()
    data class Failed(
        val message: String
    ): ToDoListState()
    data class Success(
        val result: List<ToDoListItem>
    ): ToDoListState()
}