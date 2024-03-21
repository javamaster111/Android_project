package com.example.diplomaproject.core.di

import android.content.Context
import com.example.diplomaproject.core.repository.SharedPreferencesRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideSharedPreferencesRepo(
        @ApplicationContext context: Context,
    ): SharedPreferencesRepo {
        return SharedPreferencesRepo(context)
    }
}
