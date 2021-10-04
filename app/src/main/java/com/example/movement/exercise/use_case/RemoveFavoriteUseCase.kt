package com.example.movement.exercise.use_case

import com.example.movement.exercise.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoveFavoriteUseCase @Inject constructor(private val preferencesRepository: PreferencesRepository) {
    fun removeFavorite(id: Int): Flow<Unit> {
        return preferencesRepository.remoteFavorite(id)
    }
}