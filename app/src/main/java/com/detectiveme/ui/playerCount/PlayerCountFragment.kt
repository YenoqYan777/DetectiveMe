package com.detectiveme.ui.playerCount

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.detectiveme.DetectiveMeApplication
import com.detectiveme.R
import com.detectiveme.base.BaseFragment
import com.detectiveme.base.BaseViewModel
import com.detectiveme.databinding.FragmentPlayerCountBinding
import com.detectiveme.halper.LocaleHelper
import com.detectiveme.ui.playerCount.PlayerCountViewModel.Companion.activityOpenCount
import kotlinx.android.synthetic.main.fragment_player_count.*


class PlayerCountFragment : BaseFragment(R.layout.fragment_player_count) {
    private val sharedPrefFile = "kotlinsharedpreference"
    private val LANG_KEY = "lang"

    private lateinit var binding: FragmentPlayerCountBinding
    private val viewModel: PlayerCountViewModel by lazy {
        ViewModelProviders.of(this).get(PlayerCountViewModel::class.java)
    }
    private val args: PlayerCountFragmentArgs by navArgs()

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LocaleHelper().setLocale(
            requireContext(),
            requireActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
                .getString(LANG_KEY, "en")!!
        )

    }

    override fun onStart() {
        super.onStart()
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlayerCountBinding.bind(view)
        onButtonsClickedListener()
        initViewModel()

        viewModel.activityOpened()
        if (activityOpenCount % 4 == 0) {
            DetectiveMeApplication().initAd(
                requireContext(),
                binding.imgLoading,
                binding.loadingBckg
            )
        }
    }

    private fun initViewModel() {
        binding.viewModel = viewModel
        viewModel.totalPlayer.observe(viewLifecycleOwner, Observer {
            numTotalPlayers.text = it.toString()
        })
        viewModel.totalSpies.observe(viewLifecycleOwner, Observer {
            numFakePlayers.text = it.toString()
        })

        viewModel.totalMins.observe(viewLifecycleOwner, Observer {
            numMinutes.text = it.toString()
        })
    }


    private fun onButtonsClickedListener() {
        binding.btnBack.setOnClickListener {
            viewModel.navigateBack()
        }

        binding.btnStart.setOnClickListener {
            if (numTotalPlayers.text.toString().toInt() <= numFakePlayers.text.toString().toInt()) {
                Toast.makeText(
                    requireContext(),
                    requireActivity().resources.getString(R.string.moreNumberErrorMessage),
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                val players = intArrayOf(
                    numTotalPlayers.text.toString().toInt(),
                    numFakePlayers.text.toString().toInt(),
                    numMinutes.text.toString().toInt()
                )

                viewModel.navigate(
                    PlayerCountFragmentDirections.actionPlayerCountFragmentToRoleCheckerFragment(
                        args.wordList, players
                    )
                )

            }
        }

        binding.btnMinusTotal.setOnClickListener {
            if (numTotalPlayers.text.toString().toInt() >= 4) {
                viewModel.updateTotalPlayers(false)
            }
        }

        binding.btnMinusFake.setOnClickListener {
            if (numFakePlayers.text.toString().toInt() >= 2) {
                viewModel.updateTotalSpies(false)
            }
        }

        binding.btnMinusMinute.setOnClickListener {
            if (numMinutes.text.toString().toInt() >= 2) {
                viewModel.updateTotalMins(false)
            }
        }
    }
}