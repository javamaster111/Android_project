package com.example.diplomaproject.core.repository

import android.util.Log
import com.example.diplomaproject.core.service.AuthService
import com.example.diplomaproject.core.service.LoginRequest
import com.example.diplomaproject.core.service.LoginResponse
import com.example.diplomaproject.core.service.RegistrationRequest
import com.example.diplomaproject.core.service.SendEmailVerificationRequest
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val authService: AuthService
) : UserRepository {

    override suspend fun sendEmailVerification(username: String, smsRequestType: String): Pair<String?, String?> {
        return try {
            val response = authService.sendEmailVerification(SendEmailVerificationRequest(username, smsRequestType))
            Pair(response, null)
        } catch (ex: Exception) {
            Pair(null, ex.message)
        }
    }

    override suspend fun signUp(username: String, password: String, verificationCode: String): Pair<String?, String?> {
        return try {
            val response = authService.signUp(RegistrationRequest(username, password, verificationCode))
            Log.d("msg", response)
            Pair(response, null)
        } catch (ex: Exception) {
            Pair(null, ex.message)
        }
    }

    override suspend fun signIn(username: String, password: String): Pair<LoginResponse?, String?> {
        return try {
            val response = authService.signIn(LoginRequest(username, password))
            Pair(response, null)
        } catch (ex: Exception) {
            Pair(null, ex.message)
        }
    }

}