package com.example.diplomaproject.core.di

import com.example.diplomaproject.core.repository.ProfileRepository
import com.example.diplomaproject.core.repository.ProfileRepositoryImpl
import com.example.diplomaproject.core.repository.ToDoListRepository
import com.example.diplomaproject.core.repository.ToDoListRepositoryImpl
import com.example.diplomaproject.core.repository.UserRepository
import com.example.diplomaproject.core.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun provideUserRepository(userRepo: UserRepositoryImpl): UserRepository

    @Binds
    fun provideProfileRepository(profileRepo: ProfileRepositoryImpl): ProfileRepository

    @Binds
    fun provideToDoListRepository(toDoListRepository: ToDoListRepositoryImpl): ToDoListRepository
}
