package com.example.movement.exercise.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.movement.exercise.model.Exercise
import com.example.movement.shared.pref.PreferenceKeys
import com.example.movement.shared.pref.prefsDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.lang.reflect.Type
import javax.inject.Inject


class PreferencesRepository @Inject constructor(@ApplicationContext val context: Context) {

    private val gson = Gson()

    fun saveFavorite(id: Int): Flow<Unit> {
        return flow {
            context.prefsDataStore.edit { preferences ->
                if (preferences[PreferenceKeys.FAVORITE_LIST] != null) {
                    val type: Type = object : TypeToken<MutableList<Int>?>() {}.type
                    val list = gson.fromJson<MutableList<Int>?>(preferences[PreferenceKeys.FAVORITE_LIST], type)
                    list.add(id)
                    preferences[PreferenceKeys.FAVORITE_LIST] = gson.toJson(list)
                } else {
                    preferences[PreferenceKeys.FAVORITE_LIST] = gson.toJson(listOf(id))
                }
                emit(Unit)
            }
        }
    }

    fun getFavorites(): Flow<List<Int>> {
        return context.prefsDataStore.data
            .map { preferences ->
                val type: Type = object : TypeToken<List<Int>?>() {}.type
                gson.fromJson(preferences[PreferenceKeys.FAVORITE_LIST], type) ?: listOf()
            }
    }

    fun remoteFavorite(id: Int): Flow<Unit> {
        return flow {
            context.prefsDataStore.edit { preferences ->
                if (preferences[PreferenceKeys.FAVORITE_LIST] != null) {
                    val type: Type = object : TypeToken<MutableList<Int>?>() {}.type
                    val list = gson.fromJson<MutableList<Int>?>(preferences[PreferenceKeys.FAVORITE_LIST], type)
                    list.remove(id)
                    preferences[PreferenceKeys.FAVORITE_LIST] = gson.toJson(list)
                    emit(Unit)
                }
            }
        }
    }

    fun saveSlideExercises(list: List<Exercise>): Flow<Unit> {
        return flow {
            context.prefsDataStore.edit { preferences ->
                preferences.remove(PreferenceKeys.SLIDE_EXERCISE_LIST)
                preferences[PreferenceKeys.SLIDE_EXERCISE_LIST] = gson.toJson(list)
                emit(Unit)
            }
        }
    }


    fun getSlideExercises(): Flow<List<Exercise>> {
        return context.prefsDataStore.data
            .map { preferences ->
                val type: Type = object : TypeToken<List<Exercise>?>() {}.type
                gson.fromJson(preferences[PreferenceKeys.SLIDE_EXERCISE_LIST], type) ?: listOf()
            }
    }

    fun removeSlideExercises(): Flow<Unit> {
        return flow {
            context.prefsDataStore.edit { preferences ->
                preferences.remove(PreferenceKeys.SLIDE_EXERCISE_LIST)
            }
            emit(Unit)
        }
    }
}