package com.example.diplomaproject.core.repository

import com.example.diplomaproject.core.service.LoginResponse

interface UserRepository {
    suspend fun sendEmailVerification(username: String, smsRequestType: String): Pair<String?, String?>

    suspend fun signUp(username: String, password: String, verificationCode: String): Pair<String?, String?>

    suspend fun signIn(username: String, password: String): Pair<LoginResponse?, String?>
}