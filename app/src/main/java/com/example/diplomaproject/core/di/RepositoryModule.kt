package com.example.diplomaproject.core.di

import com.example.diplomaproject.core.repository.UserRepository
import com.example.diplomaproject.core.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun provideUserRepository(userRepo: UserRepositoryImpl): UserRepository
}