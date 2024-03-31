package com.example.diplomaproject.core.repository

import com.example.diplomaproject.core.service.ToDoListItem

interface ToDoListRepository {
    suspend fun getToDoList(
        token: String,
        time: String
    ): Pair<List<ToDoListItem>?, String?>

    suspend fun addToDo(
        token: String,
        message: String,
        time: String
    ): Pair<String?, String?>
}