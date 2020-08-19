package com.detectiveme.ui.selectLang

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.detectiveme.R
import com.detectiveme.base.BaseFragment
import com.detectiveme.base.BaseViewModel
import com.detectiveme.databinding.FragmentSelectLangBinding
import com.detectiveme.util.setLocale

class SelectLangFragment : BaseFragment() {
    private lateinit var binding: FragmentSelectLangBinding
    private lateinit var viewModel: SelectLangViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_select_lang, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()

        onLangSelectedListener()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(SelectLangViewModel::class.java)

    }

    override fun getViewModel(): BaseViewModel = viewModel

    private fun onLangSelectedListener() {

        binding.btnLangArm.setOnClickListener {
            lang = "hy"
            setLocale(lang, requireContext())
            viewModel.navigate(
                SelectLangFragmentDirections.actionSelectLangFragmentToSelectTypeFragment()
            )
        }
        binding.btnLangRu.setOnClickListener {
            lang = "ru"
            setLocale(lang, requireContext())
            viewModel.navigate(
                SelectLangFragmentDirections.actionSelectLangFragmentToSelectTypeFragment()
            )
        }
        binding.btnLangUs.setOnClickListener {
            lang = "en"
            setLocale(lang, requireContext())
            viewModel.navigate(
                SelectLangFragmentDirections.actionSelectLangFragmentToSelectTypeFragment()
            )
        }
    }


}