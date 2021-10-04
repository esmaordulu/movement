package com.example.movement.shared.pref

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val PREFERENCES_NAME_USER =  "exercise_datastore_pref"

val Context.prefsDataStore by preferencesDataStore(
    name = PREFERENCES_NAME_USER
)

object PreferenceKeys {
    val FAVORITE_LIST = stringPreferencesKey("favoriteList")
    val SLIDE_EXERCISE_LIST = stringPreferencesKey("slideExerciseList")
}

