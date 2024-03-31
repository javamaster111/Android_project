package com.example.diplomaproject.core.service

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ToDoListService {

    @GET("/api/calendars")
    suspend fun getToDoList(
        @Header("Authorization") token: String,
        @Query("time") time: String,
    ): List<ToDoListItem>

    @POST("/api/calendars")
    suspend fun addToDo(
        @Header("Authorization") token: String,
        @Body request: ToDoRequest
    ): String
}

data class ToDoListItem(
    val message: String,
    val time: String
)

data class ToDoRequest(
    val message: String,
    val time: String
)