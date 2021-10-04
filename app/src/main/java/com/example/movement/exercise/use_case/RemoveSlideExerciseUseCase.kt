package com.example.movement.exercise.use_case

import com.example.movement.exercise.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoveSlideExerciseUseCase @Inject constructor(private val preferencesRepository: PreferencesRepository) {
    fun removeSlideExercise(): Flow<Unit> {
        return preferencesRepository.removeSlideExercises()
    }
}