package com.example.diplomaproject.core.repository

import com.example.diplomaproject.core.service.ToDoListItem
import com.example.diplomaproject.core.service.ToDoListService
import com.example.diplomaproject.core.service.ToDoRequest
import javax.inject.Inject

class ToDoListRepositoryImpl @Inject constructor(
    private val toDoListService: ToDoListService
): ToDoListRepository {
    override suspend fun getToDoList(
        token: String,
        time: String
    ): Pair<List<ToDoListItem>?, String?> {
        return try {
            val response = toDoListService
                .getToDoList(
                    token = "Bearer $token",
                    time = time
                )
            Pair(response, null)
        } catch (ex: Exception) {
            Pair(null, ex.message)
        }
    }

    override suspend fun addToDo(
        token: String,
        message: String,
        time: String
    ): Pair<String?, String?> {
        return try {
            val response = toDoListService
                .addToDo(
                    token = "Bearer $token",
                    request = ToDoRequest(message, time)
                )
            Pair(response, null)
        } catch (ex: Exception) {
            Pair(null, ex.message)
        }
    }
}