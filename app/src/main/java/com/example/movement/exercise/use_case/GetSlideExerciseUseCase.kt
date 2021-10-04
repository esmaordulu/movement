package com.example.movement.exercise.use_case

import com.example.movement.exercise.model.Exercise
import com.example.movement.exercise.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSlideExerciseUseCase @Inject constructor(private val preferencesRepository: PreferencesRepository) {
    fun getSlideExercises(): Flow<List<Exercise>> {
        return preferencesRepository.getSlideExercises()
    }
}
