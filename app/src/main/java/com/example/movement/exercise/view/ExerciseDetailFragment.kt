package com.example.movement.exercise.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.movement.R
import com.example.movement.databinding.ExerciseDetailFragmentBinding
import com.example.movement.exercise.view_model.ExerciseDetailViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ExerciseDetailFragment : Fragment() {

    private val viewModel by viewModels<ExerciseDetailViewModel>()

    private var binding: ExerciseDetailFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ExerciseDetailFragmentBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.exercise.observe(viewLifecycleOwner, { exercise ->
            binding?.let {
                if (context != null) {
                    Glide
                        .with(requireContext())
                        .load(exercise.imageUrl)
                        .into(it.ivExerciseImage)
                }
                if (exercise.isFavorite) {
                    binding?.btnAddFavorite?.text = getString(R.string.remove_favorite)
                } else {
                    binding?.btnAddFavorite?.text = getString(R.string.add_favorite)
                }
            }
        })

        viewModel.finish.observe(viewLifecycleOwner, {
            if (it) findNavController().popBackStack()
        })

        binding?.btnAddFavorite?.setOnClickListener {
            viewModel.addFavorite()
        }

        binding?.btnCancelTraining?.setOnClickListener {
            viewModel.cancelTraining()
        }
    }
}