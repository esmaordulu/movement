package com.example.movement.exercise.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movement.R
import com.example.movement.databinding.ItemExerciseBinding
import com.example.movement.exercise.model.Exercise

class ExerciseAdapter(
    private val context: Context,
    private val exerciseList: List<Exercise>,
    private val listener: ExerciseListener): RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    interface ExerciseListener {
        fun onFavoriteClick(exercise: Exercise)
        fun onItemClick(exercise: Exercise)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ItemExerciseBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseViewHolder(binding)
    }

    override fun getItemCount() = exerciseList.size

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        with(exerciseList[position]) {
            holder.bind(exercise = this, listener)
        }
    }

    inner class ExerciseViewHolder(
        private val binding: ItemExerciseBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(exercise: Exercise, listener: ExerciseListener) {
            Glide
                .with(context)
                .load(exercise.imageUrl)
                .fitCenter()
                .into(binding.ivExerciseImage)
            
            binding.tvExerciseName.text = exercise.name
            binding.ivFavorite.setImageResource(if (exercise.isFavorite) R.drawable.star else R.drawable.star_empty)
            binding.ivFavorite.setOnClickListener {
                listener.onFavoriteClick(exercise)
            }
            binding.root.setOnClickListener {
                listener.onItemClick(exercise)
            }
        }
    }
}

