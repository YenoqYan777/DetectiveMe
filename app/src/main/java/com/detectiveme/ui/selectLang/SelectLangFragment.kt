package com.detectiveme.ui.selectLang

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.detectiveme.R
import com.detectiveme.base.BaseFragment
import com.detectiveme.base.BaseViewModel
import com.detectiveme.databinding.FragmentSelectLangBinding
import com.detectiveme.util.buttonEffect
import java.util.*

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
        buttonEffect(binding.btnLangArm)
        buttonEffect(binding.btnLangRu)
        buttonEffect(binding.btnLangUs)

        binding.btnLangArm.setOnClickListener {
            setLocale("hy")

        }
        binding.btnLangRu.setOnClickListener {
            setLocale("ru")
        }
        binding.btnLangUs.setOnClickListener {
            setLocale("en")
        }
    }


    private fun setLocale(lang: String?) {
        val myLocale = Locale(lang)
        val res: Resources = resources
        val dm: DisplayMetrics = res.displayMetrics
        val conf: Configuration = res.configuration
        conf.locale = myLocale
        res.updateConfiguration(conf, dm)
        viewModel.navigate(
            SelectLangFragmentDirections.actionSelectLangFragmentToSelectTypeFragment()
        )
    }
}