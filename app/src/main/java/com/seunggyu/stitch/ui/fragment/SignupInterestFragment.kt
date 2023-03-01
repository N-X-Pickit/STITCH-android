package com.seunggyu.stitch.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.seunggyu.stitch.R
import com.seunggyu.stitch.databinding.FragInterestBinding
import com.seunggyu.stitch.viewModel.SignupViewModel

class SignupInterestFragment : Fragment() {
    private val viewModel by activityViewModels<SignupViewModel>()

    private val binding by lazy {
        FragInterestBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        init()

        return binding.root
    }

    fun init() {
        with(binding) {
            cvTennis.setOnClickListener {
                val isSelected: Boolean? = viewModel.checkInteresting("tennis")
                if (isSelected == false) {
                    // 선택 되었을 경우
                    cvTennis.strokeColor = ContextCompat.getColor(requireContext(), R.color.primary)
                    ivLovelyTennis.isVisible = true
                    viewModel.addInterestingSelectedItem("tennis")
                } else {
                    // 선택 해제된 경우
                    cvTennis.strokeColor = ContextCompat.getColor(requireContext(), R.color.gray_12)
                    ivLovelyTennis.isVisible = false
                    viewModel.deleteInterestingSelectedItem("tennis")
                }
            }

            cvBadminton.setOnClickListener {
                val isSelected: Boolean? = viewModel.checkInteresting("badminton")
                if (isSelected == false) {
                    cvBadminton.strokeColor = ContextCompat.getColor(requireContext(), R.color.primary)
                    ivLovelyBadminton.isVisible = true
                    viewModel.addInterestingSelectedItem("badminton")
                } else {
                    cvBadminton.strokeColor = ContextCompat.getColor(requireContext(), R.color.gray_12)
                    ivLovelyBadminton.isVisible = false
                    viewModel.deleteInterestingSelectedItem("badminton")
                }
            }

            cvPingpong.setOnClickListener {
                val isSelected: Boolean? = viewModel.checkInteresting("pingpong")
                if (isSelected == false) {
                    cvPingpong.strokeColor = ContextCompat.getColor(requireContext(), R.color.primary)
                    ivLovelyPingpong.isVisible = true
                    viewModel.addInterestingSelectedItem("pingpong")
                } else {
                    cvPingpong.strokeColor = ContextCompat.getColor(requireContext(), R.color.gray_12)
                    ivLovelyPingpong.isVisible = false
                    viewModel.deleteInterestingSelectedItem("pingpong")
                }
            }

            cvSoccer.setOnClickListener {
                val isSelected: Boolean? = viewModel.checkInteresting("soccer")
                if (isSelected == false) {
                    cvSoccer.strokeColor = ContextCompat.getColor(requireContext(), R.color.primary)
                    ivLovelySoccer.isVisible = true
                    viewModel.addInterestingSelectedItem("soccer")
                } else {
                    cvSoccer.strokeColor = ContextCompat.getColor(requireContext(), R.color.gray_12)
                    ivLovelySoccer.isVisible = false
                    viewModel.deleteInterestingSelectedItem("soccer")
                }
            }

            cvRunning.setOnClickListener {
                val isSelected: Boolean? = viewModel.checkInteresting("running")
                if (isSelected == false) {
                    cvRunning.strokeColor = ContextCompat.getColor(requireContext(), R.color.primary)
                    ivLovelyRunning.isVisible = true
                    viewModel.addInterestingSelectedItem("running")
                } else {
                    cvRunning.strokeColor = ContextCompat.getColor(requireContext(), R.color.gray_12)
                    ivLovelyRunning.isVisible = false
                    viewModel.deleteInterestingSelectedItem("running")
                }
            }

            cvMountain.setOnClickListener {
                val isSelected: Boolean? = viewModel.checkInteresting("mountain")
                if (isSelected == false) {
                    cvMountain.strokeColor = ContextCompat.getColor(requireContext(), R.color.primary)
                    ivLovelyMountain.isVisible = true
                    viewModel.addInterestingSelectedItem("mountain")
                } else {
                    cvMountain.strokeColor = ContextCompat.getColor(requireContext(), R.color.gray_12)
                    ivLovelyMountain.isVisible = false
                    viewModel.deleteInterestingSelectedItem("mountain")
                }
            }

            cvHealth.setOnClickListener {
                val isSelected: Boolean? = viewModel.checkInteresting("health")
                if (isSelected == false) {
                    cvHealth.strokeColor = ContextCompat.getColor(requireContext(), R.color.primary)
                    ivLovelyHealth.isVisible = true
                    viewModel.addInterestingSelectedItem("health")
                } else {
                    cvHealth.strokeColor = ContextCompat.getColor(requireContext(), R.color.gray_12)
                    ivLovelyHealth.isVisible = false
                    viewModel.deleteInterestingSelectedItem("health")
                }
            }

            cvBasketball.setOnClickListener {
                val isSelected: Boolean? = viewModel.checkInteresting("basketball")
                if (isSelected == false) {
                    cvBasketball.strokeColor = ContextCompat.getColor(requireContext(), R.color.primary)
                    ivLovelyBasketball.isVisible = true
                    viewModel.addInterestingSelectedItem("basketball")
                } else {
                    cvBasketball.strokeColor = ContextCompat.getColor(requireContext(), R.color.gray_12)
                    ivLovelyBasketball.isVisible = false
                    viewModel.deleteInterestingSelectedItem("basketball")
                }
            }

            cvBaseball.setOnClickListener {
                val isSelected: Boolean? = viewModel.checkInteresting("baseball")
                if (isSelected == false) {
                    cvBaseball.strokeColor = ContextCompat.getColor(requireContext(), R.color.primary)
                    ivLovelyBaseball.isVisible = true
                    viewModel.addInterestingSelectedItem("baseball")
                } else {
                    cvBaseball.strokeColor = ContextCompat.getColor(requireContext(), R.color.gray_12)
                    ivLovelyBaseball.isVisible = false
                    viewModel.deleteInterestingSelectedItem("baseball")
                }
            }

            cvGolf.setOnClickListener {
                val isSelected: Boolean? = viewModel.checkInteresting("golf")
                if (isSelected == false) {
                    cvGolf.strokeColor = ContextCompat.getColor(requireContext(), R.color.primary)
                    ivLovelyGolf.isVisible = true
                    viewModel.addInterestingSelectedItem("golf")
                } else {
                    cvGolf.strokeColor = ContextCompat.getColor(requireContext(), R.color.gray_12)
                    ivLovelyGolf.isVisible = false
                    viewModel.deleteInterestingSelectedItem("golf")
                }
            }

            cvEtc.setOnClickListener {
                val isSelected: Boolean? = viewModel.checkInteresting("etc")
                if (isSelected == false) {
                    cvEtc.strokeColor = ContextCompat.getColor(requireContext(), R.color.primary)
                    ivLovelyEtc.isVisible = true
                    viewModel.addInterestingSelectedItem("etc")
                } else {
                    cvEtc.strokeColor = ContextCompat.getColor(requireContext(), R.color.gray_12)
                    ivLovelyEtc.isVisible = false
                    viewModel.deleteInterestingSelectedItem("etc")
                }
            }
        }
    }
}