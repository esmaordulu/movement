package com.example.movement.exercise.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movement.R
import com.example.movement.exercise.model.Exercise
import com.example.movement.exercise.use_case.*
import com.example.movement.shared.view.BaseViewModel
import com.example.movement.shared.view.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseListViewModel @Inject constructor(
    private val getExerciseUseCase: GetExerciseUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    private val saveSlideExerciseUseCase: SaveSlideExerciseUseCase,
    private val removeSlideExerciseUseCase: RemoveSlideExerciseUseCase
)  : BaseViewModel() {

    val exerciseList: MutableLiveData<List<Exercise>> = MutableLiveData()
    val navigateToDetail: MutableLiveData<Event<Boolean>> = MutableLiveData()

    init {
        getExercises()
    }

    private fun getExercises() {
        viewModelScope.launch {
            displaySpinner.value = Event(true)
            getExerciseUseCase.getExercises()
                .catch { exception ->
                    Log.e("Exception", exception.localizedMessage ?: "")
                    displaySpinner.value = Event(false)
                    displayAlert.value = Event(R.string.general_error)
                }
                .collect {
                    exerciseList.value = it
                    displaySpinner.value = Event(false)
                }
        }
    }

    fun handleFavoriteClick(exercise: Exercise) {
        viewModelScope.launch {
            if (exercise.isFavorite) {
                removeFavoriteUseCase.removeFavorite(exercise.id).collect()
            } else {
                addFavoriteUseCase.addFavorite(exercise.id).collect()
            }
        }
    }

    fun handleItemClick(exercise: Exercise) {
        viewModelScope.launch {
            val list = exerciseList.value
            list?.let {
                val index = list.indexOf(exercise)
                saveSlideExerciseUseCase.saveSlideExercise(list.subList(index, list.size))
                    .catch { exception ->
                        Log.e("Exception", exception.localizedMessage ?: "")
                        displayAlert.value = Event(R.string.general_error)
                    }
                    .collect {
                        navigateToDetail.value = Event(true)
                    }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            removeSlideExerciseUseCase.removeSlideExercise()
        }
    }
}
