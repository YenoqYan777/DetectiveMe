package com.detectiveme.ui.viewRules

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.detectiveme.R
import com.detectiveme.base.BaseFragment
import com.detectiveme.base.BaseViewModel
import com.detectiveme.databinding.FragmentViewRulesBinding


class ViewRulesFragment : BaseFragment(R.layout.fragment_view_rules) {
    private lateinit var binding: FragmentViewRulesBinding
    private val viewModel: ViewRulesViewModel by lazy {
        ViewModelProviders.of(this).get(ViewRulesViewModel::class.java)
    }

    override fun getViewModel(): BaseViewModel= viewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentViewRulesBinding.inflate(inflater)

        binding.apply {
            btnBack.setOnClickListener {
                viewModel.navigateBack()
            }
        }

        return binding.root
    }

}