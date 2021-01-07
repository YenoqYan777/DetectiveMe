package com.detectiveme.ui.selectType

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModelProviders
import com.detectiveme.R
import com.detectiveme.base.BaseFragment
import com.detectiveme.base.BaseViewModel
import com.detectiveme.databinding.FragmentSelectTypeBinding
import kotlinx.android.synthetic.main.fragment_select_type.*
import kotlinx.android.synthetic.main.progress_bar.*


class SelectTypeFragment : BaseFragment(R.layout.fragment_select_type) {
    private lateinit var binding: FragmentSelectTypeBinding
    private val viewModel: SelectTypeViewModel by lazy {
        ViewModelProviders.of(this).get(SelectTypeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectTypeBinding.inflate(inflater)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onButtonsClicked()
    }

    override fun getViewModel(): BaseViewModel = viewModel

    private fun onButtonsClicked() {
        binding.btnBackList.setOnClickListener {
            viewModel.navigateBack()
        }

        binding.btnTypeAnimals.setOnClickListener {
            viewModel.navigate(
                SelectTypeFragmentDirections.actionSelectTypeFragmentToPlayerCountFragment(
                    "animals"
                )
            )
        }
        binding.btnTypePlaces.setOnClickListener {
            viewModel.navigate(
                SelectTypeFragmentDirections.actionSelectTypeFragmentToPlayerCountFragment(
                    "places"
                )
            )
        }
        binding.btnTypeProf.setOnClickListener {
            viewModel.navigate(
                SelectTypeFragmentDirections.actionSelectTypeFragmentToPlayerCountFragment(
                    "profs"
                )
            )
        }

        binding.btnRules.setOnClickListener {
            viewModel.navigate(SelectTypeFragmentDirections.actionSelectTypeFragmentToViewRulesFragment())
        }
    }
}