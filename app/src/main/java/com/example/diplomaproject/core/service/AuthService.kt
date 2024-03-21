package com.example.diplomaproject.core.service

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/api/emails")
    suspend fun sendEmailVerification(
        @Body request: SendEmailVerificationRequest,
    ): String

    @POST("/api/auth/signup")
    suspend fun signUp(
        @Body request: RegistrationRequest,
    ): String

    @POST("/api/auth/signin")
    suspend fun signIn(
        @Body request: LoginRequest,
    ): LoginResponse
}

data class SendEmailVerificationRequest(
    val email: String,
    val smsRequestType: String,
)

data class LoginRequest(
    val email: String,
    val password: String,
)

data class RegistrationRequest(
    val email: String,
    val password: String,
    val verificationCode: String,
)

data class LoginResponse(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
)
