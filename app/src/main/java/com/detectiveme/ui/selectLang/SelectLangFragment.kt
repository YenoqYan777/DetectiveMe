package com.detectiveme.ui.selectLang

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.detectiveme.R
import com.detectiveme.base.BaseFragment
import com.detectiveme.base.BaseViewModel
import com.detectiveme.databinding.FragmentSelectLangBinding
import com.detectiveme.util.setLocale

class SelectLangFragment : BaseFragment(R.layout.fragment_select_lang) {
    private lateinit var binding: FragmentSelectLangBinding
    private lateinit var viewModel: SelectLangViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSelectLangBinding.bind(view)

        initViewModel()
        onLangSelectedListener()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(SelectLangViewModel::class.java)

    }

    override fun getViewModel(): BaseViewModel = viewModel

    private fun onLangSelectedListener() {
        binding.apply {
            btnLangArm.setOnClickListener {
                lang = "hy"
                setLocale(lang, requireContext())
                viewModel.navigate(
                    SelectLangFragmentDirections.actionSelectLangFragmentToSelectTypeFragment()
                )
            }
            btnLangRu.setOnClickListener {
                lang = "ru"
                setLocale(lang, requireContext())
                viewModel.navigate(
                    SelectLangFragmentDirections.actionSelectLangFragmentToSelectTypeFragment()
                )
            }
            btnLangUs.setOnClickListener {
                lang = "en"
                setLocale(lang, requireContext())
                viewModel.navigate(
                    SelectLangFragmentDirections.actionSelectLangFragmentToSelectTypeFragment()
                )
            }
        }

    }


}