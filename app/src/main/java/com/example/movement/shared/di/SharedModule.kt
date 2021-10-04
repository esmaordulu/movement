package com.example.movement.shared.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object SharedModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return com.example.movement.shared.provideRetrofit()
    }
}