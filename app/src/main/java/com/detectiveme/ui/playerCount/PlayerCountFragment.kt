package com.detectiveme.ui.playerCount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.detectiveme.R
import com.detectiveme.base.BaseFragment
import com.detectiveme.base.BaseViewModel
import com.detectiveme.databinding.FragmentPlayerCountBinding
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.fragment_player_count.*

class PlayerCountFragment : BaseFragment(R.layout.fragment_player_count) {
    private lateinit var binding: FragmentPlayerCountBinding
    private lateinit var viewModel: PlayerCountViewModel
    private val args: PlayerCountFragmentArgs by navArgs()

    private lateinit var mInterstitialAd: InterstitialAd

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlayerCountBinding.bind(view)
        onButtonsClickedListener()
        initViewModel()
//        initAd()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(PlayerCountViewModel::class.java)
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
            binding.progressBar.visibility = VISIBLE
            if (numTotalPlayers.text.toString().toInt() <= numFakePlayers.text.toString().toInt()) {
                Toast.makeText(
                    requireContext(),
                    requireActivity().resources.getString(R.string.moreNumberErrorMessage),
                    Toast.LENGTH_SHORT
                ).show()
                binding.progressBar.visibility = GONE
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
                binding.progressBar.visibility = GONE
            }
        }

        binding.btnMinusTotal.setOnClickListener {
            if (numTotalPlayers.text.toString().toInt() >= 3) {
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
                viewModel.updateTotalMins(true)
            }
        }
    }

    private fun initAd() {
        mInterstitialAd = InterstitialAd(requireActivity())
        mInterstitialAd.adUnitId = getString(R.string.key_full)
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                if (mInterstitialAd.isLoaded) {
                    mInterstitialAd.show()
                }

            }
        }
    }
}