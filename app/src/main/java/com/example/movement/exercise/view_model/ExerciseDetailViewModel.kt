package com.example.movement.exercise.view_model

import android.os.CountDownTimer
import androidx.lifecycle.*
import com.example.movement.exercise.model.Exercise
import com.example.movement.exercise.use_case.AddFavoriteUseCase
import com.example.movement.exercise.use_case.GetSlideExerciseUseCase
import com.example.movement.exercise.use_case.RemoveFavoriteUseCase
import com.example.movement.shared.Config
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseDetailViewModel @Inject constructor(
    private val getSlideExerciseUseCase: GetSlideExerciseUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase
    ) : ViewModel() {

    private var index: Int = -1
    private lateinit var slideList: List<Exercise>
    val exercise: MutableLiveData<Exercise> = MutableLiveData()
    val finish: MutableLiveData<Boolean> = MutableLiveData()

    private lateinit var timer: CountDownTimer

    init {
        start()
    }

    private fun start() {
        viewModelScope.launch {
            slideList = getSlideExerciseUseCase.getSlideExercises().first()
            startTimer()
        }
    }

    private fun startTimer() {
        timer = object: CountDownTimer((Config.ExerciseDisplayMs * slideList.size), Config.ExerciseDisplayMs)
        {
            override fun onTick(millisUntilFinished: Long) {
                index++
                if (index >= slideList.size) {
                    cancelTraining()
                } else {
                    exercise.value = slideList[index]
                }
            }

            override fun onFinish() {
                cancelTraining()
            }
        }
        timer.start()
    }

    fun addFavorite() {
        viewModelScope.launch {
            exercise.value?.let {
                val id = it.id

                if (it.isFavorite) {
                    removeFavoriteUseCase.removeFavorite(id).collect {
                        exercise.value = exercise.value.apply {
                            if (this != null) {
                                isFavorite = !isFavorite
                            }
                        }
                    }
                } else {
                    addFavoriteUseCase.addFavorite(id).collect {
                        exercise.value = exercise.value.apply {
                            if (this != null) {
                                isFavorite = !isFavorite
                            }
                        }
                    }
                }
            }
        }
    }

    fun cancelTraining() {
        finish.value = true
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }
}