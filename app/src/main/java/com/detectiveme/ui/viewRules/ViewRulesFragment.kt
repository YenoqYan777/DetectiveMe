package com.detectiveme.ui.viewRules

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.detectiveme.R
import com.detectiveme.base.BaseFragment
import com.detectiveme.base.BaseViewModel
import com.detectiveme.databinding.FragmentViewRulesBinding
import com.detectiveme.halper.LocaleHelper


class ViewRulesFragment : BaseFragment(R.layout.fragment_view_rules) {
    private val sharedPrefFile = "kotlinsharedpreference"
    private val LANG_KEY = "lang"

    private lateinit var binding: FragmentViewRulesBinding
    private val viewModel: ViewRulesViewModel by lazy {
        ViewModelProviders.of(this).get(ViewRulesViewModel::class.java)
    }

    override fun getViewModel(): BaseViewModel= viewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LocaleHelper().setLocale(
            requireContext(),
            requireActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
                .getString(LANG_KEY, "en")!!
        )
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

    }
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