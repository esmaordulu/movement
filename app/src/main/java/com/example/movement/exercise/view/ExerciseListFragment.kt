package com.example.movement.exercise.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movement.R
import com.example.movement.databinding.ExerciseListFragmentBinding
import com.example.movement.exercise.model.Exercise
import com.example.movement.exercise.view_model.ExerciseListViewModel
import com.example.movement.shared.view.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseListFragment : BaseFragment(), ExerciseAdapter.ExerciseListener {

    private val viewModel by viewModels<ExerciseListViewModel>()
    private var binding: ExerciseListFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ExerciseListFragmentBinding.inflate(inflater)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.exerciseList.observe(viewLifecycleOwner, { list ->
            val adapter = ExerciseAdapter(requireContext(), list, this)
            binding?.rvExerciseList?.run {
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                this.adapter = adapter
            }
        })
        viewModel.navigateToDetail.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { navigate ->
                if (navigate) findNavController().navigate(R.id.action_exerciseListFragment_to_exerciseDetailFragment)
            }
        })

        viewModel.displaySpinner.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { display ->
                if (display) showSpinner() else dismissSpinner()
            }
        })

        viewModel.displayAlert.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { messageId ->
                alertShow(bodyMsg = getString(messageId))
            }
        })
    }

    override fun onFavoriteClick(exercise: Exercise) {
        viewModel.handleFavoriteClick(exercise)
    }

    override fun onItemClick(exercise: Exercise) {
        viewModel.handleItemClick(exercise)
    }
}