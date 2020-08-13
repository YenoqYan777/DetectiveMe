package com.detectiveme.ui.roleChecker

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.detectiveme.R
import com.detectiveme.base.BaseFragment
import com.detectiveme.base.BaseViewModel
import com.detectiveme.databinding.FragmentRoleCheckerBinding
import com.detectiveme.util.setLocale
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import java.util.*

class RoleCheckerFragment : BaseFragment() {
    private lateinit var binding: FragmentRoleCheckerBinding
    private val viewModel: RoleCheckerViewModel by lazy {
        ViewModelProviders.of(this).get(RoleCheckerViewModel::class.java)
    }
    private lateinit var mInterstitialAd: InterstitialAd

    private val args: RoleCheckerFragmentArgs by navArgs()
    private lateinit var KEY: String
    private lateinit var players: IntArray
    private var isRoleVisible = false
    private var isTimerStarted: Boolean? = null

    private val random = Random()
    private var totalP = 0
    private var fakeP = 0
    private var totalNormalP = 0
    private var mins = 0
    private var count = 1
    private var fakeCount = 1
    private var normPlayerCount = 1

    private lateinit var animals: List<String>
    private lateinit var profs: List<String>
    private lateinit var places: List<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_role_checker, container, false)
        initAd()
        setLocale(lang, requireContext())
        KEY = args.wordList
        players = args.players
        totalP = players[0]
        fakeP = players[1]
        mins = players[2]
        totalNormalP = players[0] - fakeP

        hideShowRole()

        viewModel.getRandomWordToShow(
            when (KEY) {
                "animals" -> animals
                "profs" -> profs
                else -> places
            }
        )
        binding.txtRole.visibility = INVISIBLE
        binding.btnSeeHide.text = resources.getString(R.string.show)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        animals = requireContext().resources.getStringArray(R.array.animals).toList()
        profs = requireContext().resources.getStringArray(R.array.profs).toList()
        places = requireContext().resources.getStringArray(R.array.places).toList()
    }

    override fun getViewModel(): BaseViewModel = viewModel

    private fun initAd() {
        MobileAds.initialize(requireContext()) {}
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

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

    private fun roleIsVisible() {
        if (count > totalP) {
            if (isTimerStarted != null) {
                if (isTimerStarted == true) {
                    viewModel.navigateBack()
                } else {
                    startTimer()
                }
            } else {
                binding.txtRole.text = mins.toString()
                binding.btnSeeHide.text = resources.getString(R.string.start_timer)
                binding.txtRole.visibility = VISIBLE

                isTimerStarted = false
            }

        } else {
            binding.btnSeeHide.text = resources.getString(R.string.show)
            binding.txtRole.visibility = INVISIBLE

            isRoleVisible = false
        }
    }

    private fun startTimer() {
        object : CountDownTimer((mins.toString().toLong()) * 60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 60000
                val seconds = millisUntilFinished % 60000 / 1000
                val textToShowForTimer = "$minutes : $seconds"
                binding.txtRole.text = textToShowForTimer
            }

            override fun onFinish() {
                viewModel.navigateBack()
            }
        }.start()
        binding.btnSeeHide.text =
            requireActivity().resources.getString(R.string.stop_timer)
        binding.txtRole.visibility = VISIBLE
        isTimerStarted = true
    }

    private fun roleNotVisible() {
        if (count <= totalP) {
            if (fakeCount <= fakeP && normPlayerCount <= totalNormalP) {
                val chooser = random.nextBoolean()
                if (chooser) {
                    binding.txtRole.text = resources.getString(R.string.spy)
                    fakeCount++
                } else {
                    binding.txtRole.text = viewModel.wordToShow
                    normPlayerCount++
                }
            } else if (fakeCount > fakeP) {
                binding.txtRole.text = viewModel.wordToShow
            } else {
                binding.txtRole.text = resources.getString(R.string.spy)
            }
            count++
            binding.txtRole.visibility = VISIBLE
            binding.btnSeeHide.text = resources.getString(R.string.hide)
            isRoleVisible = true
        }
    }

    private fun hideShowRole() {
        binding.btnSeeHide.setOnClickListener {
            if (isRoleVisible) {
                roleIsVisible()
            } else {
                roleNotVisible()
            }
        }
    }
}