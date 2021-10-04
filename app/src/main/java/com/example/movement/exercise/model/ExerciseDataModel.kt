package com.example.movement.exercise.model

import com.squareup.moshi.Json

data class ExerciseDataModel(
    @field:Json(name = "id") val id : Int,
    @field:Json(name = "name") val name : String,
    @field:Json(name = "cover_image_url") val imageUrl : String,
    @field:Json(name = "video_url") val videoUrl : String
)
