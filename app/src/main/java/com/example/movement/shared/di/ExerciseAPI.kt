package com.example.movement.shared.di

import com.example.movement.exercise.model.ExerciseDataModel
import retrofit2.http.GET

interface ExerciseAPI {
    @GET("jsonBlob/027787de-c76e-11eb-ae0a-39a1b8479ec2")
    suspend fun getExercises(): List<ExerciseDataModel>
}