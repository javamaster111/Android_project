package com.example.diplomaproject.core.service

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ProfileService {
    @Multipart
    @POST("/api/profiles")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Part("username") username: RequestBody,
        @Part("dateOfBirthday") dateOfBirthday: RequestBody,
        @Part("country") country: RequestBody,
        @Part profileImage: MultipartBody.Part,
    ): String
}
