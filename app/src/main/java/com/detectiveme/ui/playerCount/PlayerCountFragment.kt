package com.detectiveme.ui.playerCount

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.detectiveme.R
import com.detectiveme.base.BaseFragment
import com.detectiveme.base.BaseViewModel
import com.detectiveme.databinding.FragmentPlayerCountBinding
import com.detectiveme.util.buttonEffect
import kotlinx.android.synthetic.main.fragment_player_count.*

class PlayerCountFragment : BaseFragment() {
    private lateinit var binding: FragmentPlayerCountBinding
    private val viewModel: PlayerCountViewModel by lazy {
        ViewModelProviders.of(this).get(PlayerCountViewModel::class.java)
    }
    private val args: PlayerCountFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_player_count, container, false
        )
        return binding.root
    }


    override fun getViewModel(): BaseViewModel = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onButtonsClickedListener()
    }

    private fun onButtonsClickedListener() {
        buttonEffect(binding.btnBack)
        binding.btnBack.setOnClickListener {
            viewModel.navigateBack()
        }

        binding.btnStart.setOnClickListener {
            if (numTotalPlayers.text.toString().toInt() < numFakePlayers.text.toString().toInt()) {
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

        binding.btnAddTotal.setOnClickListener {
            binding.numTotalPlayers.text = (numTotalPlayers.text.toString().toInt() + 1).toString()
        }

        binding.btnAddFake.setOnClickListener {
            binding.numFakePlayers.text = (numFakePlayers.text.toString().toInt() + 1).toString()
        }

        binding.btnAddMinute.setOnClickListener {
            binding.numMinutes.text = (numMinutes.text.toString().toInt() + 1).toString()
        }

        binding.btnMinusTotal.setOnClickListener {
            if (numTotalPlayers.text.toString().toInt() >= 3) {
                binding.numTotalPlayers.text =
                    (numTotalPlayers.text.toString().toInt() - 1).toString()
            }
        }

        binding.btnMinusFake.setOnClickListener {
            if (numFakePlayers.text.toString().toInt() >= 2) {
                binding.numFakePlayers.text =
                    (numFakePlayers.text.toString().toInt() - 1).toString()
            }
        }

        binding.btnMinusMinute.setOnClickListener {
            if (numMinutes.text.toString().toInt() >= 2) {
                binding.numMinutes.text =
                    (numMinutes.text.toString().toInt() - 1).toString()
            }
        }
    }
}