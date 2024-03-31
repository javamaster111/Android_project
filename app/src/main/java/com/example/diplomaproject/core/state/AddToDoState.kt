package com.example.diplomaproject.core.state

sealed class AddToDoState {
    data object Loading: AddToDoState()
    data class Failed(
        val message: String
    ): AddToDoState()
    data class Success(
        val message: String
    ): AddToDoState()
}