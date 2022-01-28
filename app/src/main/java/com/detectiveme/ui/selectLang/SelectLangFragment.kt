package com.detectiveme.ui.selectLang

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.detectiveme.R
import com.detectiveme.base.BaseFragment
import com.detectiveme.base.BaseViewModel
import com.detectiveme.databinding.FragmentSelectLangBinding
import com.detectiveme.halper.LocaleHelper
import kotlinx.android.synthetic.main.fragment_select_lang.*

class SelectLangFragment : BaseFragment(R.layout.fragment_select_lang) {
    private lateinit var binding: FragmentSelectLangBinding
    private lateinit var viewModel: SelectLangViewModel

    private val sharedPrefFile = "kotlinsharedpreference"
    private val LANG_KEY = "lang"
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LocaleHelper().setLocale(
            requireContext(),
            requireActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
                .getString(LANG_KEY, "en")!!
        )
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSelectLangBinding.bind(view)
        initSharedPref()
        initViewModel()
        onLangSelectedListener()
    }

    private fun initSharedPref() {
        sharedPreferences = requireActivity().getSharedPreferences(
            sharedPrefFile,
            Context.MODE_PRIVATE
        )
    }


    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(SelectLangViewModel::class.java)

    }

    override fun getViewModel(): BaseViewModel = viewModel

    private fun onLangSelectedListener() {
        binding.apply {
            btnLangArm.setOnClickListener {
                sharedPreferences.edit().putString(LANG_KEY, "hy").apply()
                LocaleHelper().setLocale(
                    requireContext(),
                    sharedPreferences.getString(LANG_KEY, "en")!!
                )
                viewModel.navigate(
                    SelectLangFragmentDirections.actionSelectLangFragmentToSelectTypeFragment()
                )
            }
            btnLangRu.setOnClickListener {
                sharedPreferences.edit().putString(LANG_KEY, "ru").apply()
                LocaleHelper().setLocale(
                    requireContext(),
                    sharedPreferences.getString(LANG_KEY, "en")!!
                )

                viewModel.navigate(
                    SelectLangFragmentDirections.actionSelectLangFragmentToSelectTypeFragment()
                )
            }
            btnLangUs.setOnClickListener {
                sharedPreferences.edit().putString(LANG_KEY, "en").apply()
                LocaleHelper().setLocale(
                    requireContext(),
                    sharedPreferences.getString(LANG_KEY, "en")!!
                )

                viewModel.navigate(
                    SelectLangFragmentDirections.actionSelectLangFragmentToSelectTypeFragment()
                )
            }
            btnLangEs.setOnClickListener {
                sharedPreferences.edit().putString(LANG_KEY, "es").apply()
                LocaleHelper().setLocale(
                    requireContext(),
                    sharedPreferences.getString(LANG_KEY, "en")!!
                )

                viewModel.navigate(
                    SelectLangFragmentDirections.actionSelectLangFragmentToSelectTypeFragment()
                )
            }
        }

    }


}