package com.detectiveme.ui.roleChecker

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.detectiveme.R
import com.detectiveme.base.BaseFragment
import com.detectiveme.base.BaseViewModel
import com.detectiveme.databinding.FragmentRoleCheckerBinding
import java.util.*

class RoleCheckerFragment : BaseFragment() {
    private lateinit var binding: FragmentRoleCheckerBinding
    private val viewModel: RoleCheckerViewModel by lazy {
        ViewModelProviders.of(this).get(RoleCheckerViewModel::class.java)
    }
    private val args: RoleCheckerFragmentArgs by navArgs()
    private lateinit var KEY: String
    private lateinit var players: IntArray
    private var wordToshow = ""
    private var isRoleVisible = false

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
        KEY = args.wordList
        players = args.players
        totalP = players[0]
        fakeP = players[1]
        mins = players[2]
        totalNormalP = players[0] - fakeP
        getRandomWordToShow()
        hideShowRole()
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

    private fun getRandomWordToShow() {
        val random = Random()
        when (KEY) {
            "animals" -> {
                val ranNum: Int = random.nextInt(animals.size)
                wordToshow = animals[ranNum]
            }
            "profs" -> {
                val ranNum: Int = random.nextInt(profs.size)
                wordToshow = profs[ranNum]
            }
            "places" -> {
                val ranNum: Int = random.nextInt(places.size)
                wordToshow = places[ranNum]
            }
        }
    }


    private fun hideShowRole() {
        binding.btnSeeHide.setOnClickListener {
            if (isRoleVisible) {
                binding.txtRole.visibility = INVISIBLE
                binding.btnSeeHide.text = resources.getString(R.string.show)
                isRoleVisible = false
            } else {
                if (count <= totalP) {
                    if (fakeCount <= fakeP && normPlayerCount <= totalNormalP) {
                        val chooser = random.nextBoolean()
                        if (chooser) {
                            binding.txtRole.text = resources.getString(R.string.spy)
                            fakeCount++
                        } else {
                            binding.txtRole.text = wordToshow
                            normPlayerCount++
                        }
                    } else if (fakeCount > fakeP) {
                        binding.txtRole.text = wordToshow
                    } else {
                        binding.txtRole.text = resources.getString(R.string.spy)
                    }
                    count++
                } else {
                    binding.txtRole.text = "Finish"
                }

                binding.txtRole.visibility = VISIBLE
                binding.btnSeeHide.text = resources.getString(R.string.hide)
                isRoleVisible = true
            }
        }
    }
}