package com.example.movement.exercise.di

import com.example.movement.exercise.repository.ExerciseRepository
import com.example.movement.exercise.repository.ExerciseRepositoryImpl
import com.example.movement.shared.createWebService
import com.example.movement.shared.di.ExerciseAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object ExerciseModule {
    @Provides
    fun provideExerciseAPI(retrofit: Retrofit) : ExerciseAPI {
        return createWebService(retrofit = retrofit)
    }

    @Provides
    fun provideCountryRepository(exerciseAPI: ExerciseAPI) : ExerciseRepository {
        return ExerciseRepositoryImpl(exerciseAPI)
    }
}