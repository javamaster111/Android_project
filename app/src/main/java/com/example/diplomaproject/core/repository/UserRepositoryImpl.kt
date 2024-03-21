package com.example.diplomaproject.core.repository

import android.util.Log
import com.example.diplomaproject.core.service.AuthService
import com.example.diplomaproject.core.service.LoginRequest
import com.example.diplomaproject.core.service.LoginResponse
import com.example.diplomaproject.core.service.RegistrationRequest
import com.example.diplomaproject.core.service.SendEmailVerificationRequest
import javax.inject.Inject

class UserRepositoryImpl
    @Inject
    constructor(
        private val authService: AuthService,
    ) : UserRepository {
        override suspend fun sendEmailVerification(
            email: String,
            smsRequestType: String,
        ): Pair<String?, String?> {
            return try {
                val response = authService.sendEmailVerification(SendEmailVerificationRequest(email, smsRequestType))
                Pair(response, null)
            } catch (ex: Exception) {
                Pair(null, ex.message)
            }
        }

        override suspend fun signUp(
            email: String,
            password: String,
            verificationCode: String,
        ): Pair<String?, String?> {
            return try {
                val response = authService.signUp(RegistrationRequest(email, password, verificationCode))
                Log.d("msg", response)
                Pair(response, null)
            } catch (ex: Exception) {
                Pair(null, ex.message)
            }
        }

        override suspend fun signIn(
            email: String,
            password: String,
        ): Pair<LoginResponse?, String?> {
            return try {
                val response = authService.signIn(LoginRequest(email, password))
                Pair(response, null)
            } catch (ex: Exception) {
                Pair(null, ex.message)
            }
        }
    }
