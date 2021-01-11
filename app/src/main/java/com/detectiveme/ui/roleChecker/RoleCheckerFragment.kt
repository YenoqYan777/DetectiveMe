package com.detectiveme.ui.roleChecker

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.detectiveme.R
import com.detectiveme.base.BaseFragment
import com.detectiveme.base.BaseViewModel
import com.detectiveme.databinding.FragmentRoleCheckerBinding
import com.detectiveme.halper.LocaleHelper
import com.detectiveme.ui.roleChecker.RoleCheckerViewModel.Companion.wordToShow
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import java.util.*

class RoleCheckerFragment : BaseFragment(R.layout.fragment_role_checker) {
    private val sharedPrefFile = "kotlinsharedpreference"
    private val LANG_KEY = "lang"

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

    private var spy = ""
    private var show = ""
    private var hide = ""
    private var finish = ""
    private var startTimer = ""


    private lateinit var animals: List<String>
    private lateinit var profs: List<String>
    private lateinit var places: List<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoleCheckerBinding.inflate(inflater)
        binding.apply {
            txtRole.visibility = INVISIBLE
            btnSeeHide.text = resources.getString(R.string.show)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAd()
        hideShowRole()
        KEY = args.wordList
        players = args.players
        totalP = players[0]
        fakeP = players[1]
        mins = players[2]
        totalNormalP = players[0] - fakeP
        viewModel.getRandomWordToShow(
            when (KEY) {
                "animals" -> animals
                "profs" -> profs
                else -> places
            }
        )

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        LocaleHelper().setLocale(
            requireContext(),
            requireActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
                .getString(LANG_KEY, "en")!!
        )

        animals = requireContext().resources.getStringArray(R.array.animals).toList()
        profs = requireContext().resources.getStringArray(R.array.profs).toList()
        places = requireContext().resources.getStringArray(R.array.places).toList()

        spy = requireContext().resources.getString(R.string.spy)
        show = requireContext().resources.getString(R.string.show)
        hide = requireContext().resources.getString(R.string.hide)
        finish = requireContext().resources.getString(R.string.finish)
        startTimer = requireContext().resources.getString(R.string.start_timer)

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        onCreate(savedInstanceState)
    }
    override fun getViewModel(): BaseViewModel = viewModel

    private fun initAd() {
        MobileAds.initialize(requireContext()) {}
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
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
                binding.btnSeeHide.text = startTimer
                binding.txtRole.visibility = VISIBLE

                isTimerStarted = false
            }

        } else {
            binding.btnSeeHide.text = show
            binding.txtRole.visibility = INVISIBLE

            isRoleVisible = false
        }
    }

    private fun roleNotVisible() {
        if (count <= totalP) {
            if (fakeCount <= fakeP && normPlayerCount <= totalNormalP) {
                val chooser = random.nextBoolean()
                if (chooser) {
                    binding.txtRole.text = spy
                    fakeCount++
                } else {
                    binding.txtRole.text = wordToShow
                    normPlayerCount++
                }
            } else if (fakeCount > fakeP) {
                binding.txtRole.text =  wordToShow
            } else {
                binding.txtRole.text = spy
            }
            count++
            binding.txtRole.visibility = VISIBLE
            binding.btnSeeHide.text = hide
            isRoleVisible = true
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
        binding.btnSeeHide.text = finish
        binding.txtRole.visibility = VISIBLE
        isTimerStarted = true
    }


}