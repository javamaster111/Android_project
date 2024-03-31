@file:Suppress("ktlint:standard:import-ordering")

package com.example.diplomaproject.core.di

import com.example.diplomaproject.core.repository.ProfileRepositoryImpl
import com.example.diplomaproject.core.repository.ToDoListRepositoryImpl
import com.example.diplomaproject.core.repository.UserRepositoryImpl
import com.example.diplomaproject.core.service.AuthService
import com.example.diplomaproject.core.service.ProfileService
import com.example.diplomaproject.core.service.ToDoListService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "http://138.68.125.57:8080"

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().apply {
            this.addInterceptor(httpLoggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
        }.build()

    @Singleton
    @Provides
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthService = retrofit.create(AuthService::class.java)

    @Singleton
    @Provides
    fun provideUserRepo(authService: AuthService) = UserRepositoryImpl(authService)

    @Singleton
    @Provides
    fun provideProfileApi(retrofit: Retrofit): ProfileService = retrofit.create(ProfileService::class.java)

    @Singleton
    @Provides
    fun provideProfileRepo(profileService: ProfileService) = ProfileRepositoryImpl(profileService)

    @Singleton
    @Provides
    fun provideToDoListApi(retrofit: Retrofit): ToDoListService = retrofit.create(ToDoListService::class.java)

    @Singleton
    @Provides
    fun provideToDoListRepo(toDoListService: ToDoListService) = ToDoListRepositoryImpl(toDoListService)
}
