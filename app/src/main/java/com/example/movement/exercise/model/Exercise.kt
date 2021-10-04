package com.example.movement.exercise.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Exercise(
    val id : Int,
    val name : String,
    val imageUrl : String,
    val videoUrl : String,
    var isFavorite: Boolean
): Parcelable
