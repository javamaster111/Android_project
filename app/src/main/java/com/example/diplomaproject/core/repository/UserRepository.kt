package com.example.diplomaproject.core.repository

import com.example.diplomaproject.core.service.LoginResponse

interface UserRepository {
    suspend fun sendEmailVerification(
        email: String,
        smsRequestType: String,
    ): Pair<String?, String?>

    suspend fun signUp(
        email: String,
        password: String,
        verificationCode: String,
    ): Pair<String?, String?>

    suspend fun signIn(
        email: String,
        password: String,
    ): Pair<LoginResponse?, String?>
}
