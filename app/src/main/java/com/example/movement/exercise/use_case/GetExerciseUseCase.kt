package com.example.movement.exercise.use_case

import com.example.movement.exercise.model.Exercise
import com.example.movement.exercise.model.ExerciseDataModel
import com.example.movement.exercise.repository.ExerciseRepository
import com.example.movement.exercise.repository.PreferencesRepository
import kotlinx.coroutines.flow.*
import java.lang.Exception
import javax.inject.Inject

class GetExerciseUseCase @Inject constructor(
    private val exerciseRepository: ExerciseRepository,
    private val preferencesRepository: PreferencesRepository
) {
    suspend fun getExercises(): Flow<List<Exercise>> {
        val exercises: List<ExerciseDataModel>
        try {
            exercises = exerciseRepository.getExercises()
            return preferencesRepository.getFavorites()
                .map {  favList ->
                    val list: MutableList<Exercise> = mutableListOf()
                    exercises.forEach {
                        list.add(
                            Exercise(
                                id = it.id,
                                name = it.name,
                                videoUrl = it.videoUrl,
                                imageUrl = it.imageUrl,
                                isFavorite = favList.contains(it.id)
                            )
                        )
                    }
                    list
                }
        } catch (e: Exception) {
            return flow {
                throw e
            }
        }
    }
}