package com.example.movement.exercise.view_model

import android.os.CountDownTimer
import androidx.lifecycle.*
import com.example.movement.exercise.model.Exercise
import com.example.movement.exercise.use_case.AddFavoriteUseCase
import com.example.movement.exercise.use_case.GetSlideExerciseUseCase
import com.example.movement.exercise.use_case.RemoveFavoriteUseCase
import com.example.movement.exercise.use_case.RemoveSlideExerciseUseCase
import com.example.movement.shared.Config
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseDetailViewModel @Inject constructor(
    private val getSlideExerciseUseCase: GetSlideExerciseUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase,
    private val removeSlideExerciseUseCase: RemoveSlideExerciseUseCase
) : ViewModel() {

    private var index: Int
    private lateinit var slideList: List<Exercise>
    val exercise: MutableLiveData<Exercise> = MutableLiveData()
    val finish: MutableLiveData<Boolean> = MutableLiveData()

    var timerStarted = false
    private lateinit var timer: CountDownTimer

    init {
        start()
        index = -1
    }

    private fun start() {
        viewModelScope.launch {
            getSlideExerciseUseCase.getSlideExercises()
                .collect {
                    slideList = it
                    if (!timerStarted) {
                        startTimer()
                        timerStarted = true
                    }
                }
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
                timerStarted = false
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

    fun destroyView() {
        viewModelScope.launch {
            removeSlideExerciseUseCase.removeSlideExercise()
        }
    }
}