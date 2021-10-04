package com.example.movement.exercise.use_case

import com.example.movement.exercise.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(private val preferencesRepository: PreferencesRepository) {
    fun addFavorite(id: Int): Flow<Unit> {
        return preferencesRepository.saveFavorite(id)
    }
}