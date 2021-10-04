package com.example.movement.exercise.use_case

import com.example.movement.exercise.model.Exercise
import com.example.movement.exercise.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveSlideExerciseUseCase @Inject constructor(private val preferencesRepository: PreferencesRepository) {
    fun saveSlideExercise(list: List<Exercise>): Flow<Unit> {
        return preferencesRepository.saveSlideExercises(list)
    }
}