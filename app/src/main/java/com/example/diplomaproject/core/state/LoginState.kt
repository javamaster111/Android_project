package com.example.diplomaproject.core.state

import com.example.diplomaproject.core.service.LoginResponse

sealed class LoginState {

    data object Loading: LoginState()

    data class Failed(
        val message: String
    ): LoginState()

    data class Success(
        val tokens: LoginResponse
    ): LoginState()

}