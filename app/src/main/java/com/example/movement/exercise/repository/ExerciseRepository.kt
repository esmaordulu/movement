package com.example.movement.exercise.repository

import com.example.movement.exercise.model.ExerciseDataModel
import com.example.movement.shared.di.ExerciseAPI
import javax.inject.Inject

interface ExerciseRepository {
    suspend fun getExercises(): List<ExerciseDataModel>
}

class ExerciseRepositoryImpl @Inject constructor(
    private val exerciseAPI: ExerciseAPI
) : ExerciseRepository {
    override suspend fun getExercises(): List<ExerciseDataModel> {
        return exerciseAPI.getExercises()
    }
}