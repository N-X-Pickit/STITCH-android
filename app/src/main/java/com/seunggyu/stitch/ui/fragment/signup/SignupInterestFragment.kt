package com.seunggyu.stitch.ui.fragment.signup

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.renderscript.Allocation
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.seunggyu.stitch.R
import com.seunggyu.stitch.databinding.FragInterestBinding
import com.seunggyu.stitch.viewModel.SignupViewModel
import android.renderscript.Element
import jp.wasabeef.blurry.Blurry

@Suppress("DEPRECATION")
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
                    viewTransparentTennis.isVisible = true
                    Blurry.with(binding.cvTennis.context).radius(6).sampling(2).onto(binding.cvTennis)

                    ivLovelyTennis.isVisible = true
                    cvTennis.strokeColor = ContextCompat.getColor(requireContext(), R.color.primary)
                    viewModel.addInterestingSelectedItem("tennis")
                } else {
                    // 선택 해제된 경우
                    viewTransparentTennis.isVisible = false
                    Blurry.delete(binding.cvTennis)

                    cvTennis.strokeColor = ContextCompat.getColor(requireContext(), R.color.gray_12)
                    ivLovelyTennis.isVisible = false
                    viewModel.deleteInterestingSelectedItem("tennis")
                }
            }

            cvBadminton.setOnClickListener {
                val isSelected: Boolean? = viewModel.checkInteresting("badminton")
                if (isSelected == false) {
                    // 선택 되었을 경우
                    viewTransparentBadminton.isVisible = true
                    Blurry.with(binding.cvBadminton.context).radius(6).sampling(2).onto(binding.cvBadminton)

                    ivLovelyBadminton.isVisible = true
                    cvBadminton.strokeColor = ContextCompat.getColor(requireContext(), R.color.primary)
                    viewModel.addInterestingSelectedItem("badminton")
                } else {
                    // 선택 해제된 경우
                    viewTransparentBadminton.isVisible = false
                    Blurry.delete(binding.cvBadminton)

                    cvBadminton.strokeColor = ContextCompat.getColor(requireContext(), R.color.gray_12)
                    ivLovelyBadminton.isVisible = false
                    viewModel.deleteInterestingSelectedItem("badminton")
                }
            }

            cvPingpong.setOnClickListener {
                val isSelected: Boolean? = viewModel.checkInteresting("pingpong")
                if (isSelected == false) {
                    // 선택 되었을 경우
                    viewTransparentPingpong.isVisible = true
                    Blurry.with(binding.cvPingpong.context).radius(6).sampling(2).onto(binding.cvPingpong)

                    ivLovelyPingpong.isVisible = true
                    cvPingpong.strokeColor = ContextCompat.getColor(requireContext(), R.color.primary)
                    viewModel.addInterestingSelectedItem("pingpong")
                } else {
                    // 선택 해제된 경우
                    viewTransparentPingpong.isVisible = false
                    Blurry.delete(binding.cvPingpong)

                    cvPingpong.strokeColor = ContextCompat.getColor(requireContext(), R.color.gray_12)
                    ivLovelyPingpong.isVisible = false
                    viewModel.deleteInterestingSelectedItem("pingpong")
                }
            }

            cvSoccer.setOnClickListener {
                val isSelected: Boolean? = viewModel.checkInteresting("soccer")
                if (isSelected == false) {
                    // 선택 되었을 경우
                    viewTransparentSoccer.isVisible = true
                    Blurry.with(binding.cvSoccer.context).radius(6).sampling(2).onto(binding.cvSoccer)

                    ivLovelySoccer.isVisible = true
                    cvSoccer.strokeColor = ContextCompat.getColor(requireContext(), R.color.primary)
                    viewModel.addInterestingSelectedItem("soccer")
                } else {
                    // 선택 해제된 경우
                    viewTransparentSoccer.isVisible = false
                    Blurry.delete(binding.cvSoccer)

                    cvSoccer.strokeColor = ContextCompat.getColor(requireContext(), R.color.gray_12)
                    ivLovelySoccer.isVisible = false
                    viewModel.deleteInterestingSelectedItem("soccer")
                }
            }

            cvRunning.setOnClickListener {
                val isSelected: Boolean? = viewModel.checkInteresting("running")
                if (isSelected == false) {
                    // 선택 되었을 경우
                    viewTransparentRunning.isVisible = true
                    Blurry.with(binding.cvRunning.context).radius(6).sampling(2).onto(binding.cvRunning)

                    ivLovelyRunning.isVisible = true
                    cvRunning.strokeColor = ContextCompat.getColor(requireContext(), R.color.primary)
                    viewModel.addInterestingSelectedItem("running")
                } else {
                    // 선택 해제된 경우
                    viewTransparentRunning.isVisible = false
                    Blurry.delete(binding.cvRunning)

                    cvRunning.strokeColor = ContextCompat.getColor(requireContext(), R.color.gray_12)
                    ivLovelyRunning.isVisible = false
                    viewModel.deleteInterestingSelectedItem("running")
                }
            }

            cvMountain.setOnClickListener {
                val isSelected: Boolean? = viewModel.checkInteresting("mountain")
                if (isSelected == false) {
                    // 선택 되었을 경우
                    viewTransparentMountain.isVisible = true
                    Blurry.with(binding.cvMountain.context).radius(6).sampling(2).onto(binding.cvMountain)

                    ivLovelyMountain.isVisible = true
                    cvMountain.strokeColor = ContextCompat.getColor(requireContext(), R.color.primary)
                    viewModel.addInterestingSelectedItem("mountain")
                } else {
                    // 선택 해제된 경우
                    viewTransparentMountain.isVisible = false
                    Blurry.delete(binding.cvMountain)

                    cvMountain.strokeColor = ContextCompat.getColor(requireContext(), R.color.gray_12)
                    ivLovelyMountain.isVisible = false
                    viewModel.deleteInterestingSelectedItem("mountain")
                }
            }

            cvHealth.setOnClickListener {
                val isSelected: Boolean? = viewModel.checkInteresting("health")
                if (isSelected == false) {
                    // 선택 되었을 경우
                    viewTransparentHealth.isVisible = true
                    Blurry.with(binding.cvHealth.context).radius(6).sampling(2).onto(binding.cvHealth)

                    ivLovelyHealth.isVisible = true
                    cvHealth.strokeColor = ContextCompat.getColor(requireContext(), R.color.primary)
                    viewModel.addInterestingSelectedItem("health")
                } else {
                    // 선택 해제된 경우
                    viewTransparentHealth.isVisible = false
                    Blurry.delete(binding.cvHealth)

                    cvHealth.strokeColor = ContextCompat.getColor(requireContext(), R.color.gray_12)
                    ivLovelyHealth.isVisible = false
                    viewModel.deleteInterestingSelectedItem("health")
                }
            }

            cvBasketball.setOnClickListener {
                val isSelected: Boolean? = viewModel.checkInteresting("basketball")
                if (isSelected == false) {
                    // 선택 되었을 경우
                    viewTransparentBasketball.isVisible = true
                    Blurry.with(binding.cvBasketball.context).radius(6).sampling(2).onto(binding.cvBasketball)

                    ivLovelyBasketball.isVisible = true
                    cvBasketball.strokeColor = ContextCompat.getColor(requireContext(), R.color.primary)
                    viewModel.addInterestingSelectedItem("basketball")
                } else {
                    // 선택 해제된 경우
                    viewTransparentBasketball.isVisible = false
                    Blurry.delete(binding.cvBasketball)

                    cvBasketball.strokeColor = ContextCompat.getColor(requireContext(), R.color.gray_12)
                    ivLovelyBasketball.isVisible = false
                    viewModel.deleteInterestingSelectedItem("basketball")
                }
            }

            cvBaseball.setOnClickListener {
                val isSelected: Boolean? = viewModel.checkInteresting("baseball")
                if (isSelected == false) {
                    // 선택 되었을 경우
                    viewTransparentBaseball.isVisible = true
                    Blurry.with(binding.cvBaseball.context).radius(6).sampling(2).onto(binding.cvBaseball)

                    ivLovelyBaseball.isVisible = true
                    cvBaseball.strokeColor = ContextCompat.getColor(requireContext(), R.color.primary)
                    viewModel.addInterestingSelectedItem("baseball")
                } else {
                    // 선택 해제된 경우
                    viewTransparentBaseball.isVisible = false
                    Blurry.delete(binding.cvBaseball)

                    cvBaseball.strokeColor = ContextCompat.getColor(requireContext(), R.color.gray_12)
                    ivLovelyBaseball.isVisible = false
                    viewModel.deleteInterestingSelectedItem("baseball")
                }
            }

            cvGolf.setOnClickListener {
                val isSelected: Boolean? = viewModel.checkInteresting("golf")
                if (isSelected == false) {
                    // 선택 되었을 경우
                    viewTransparentGolf.isVisible = true
                    Blurry.with(binding.cvGolf.context).radius(6).sampling(2).onto(binding.cvGolf)

                    ivLovelyGolf.isVisible = true
                    cvGolf.strokeColor = ContextCompat.getColor(requireContext(), R.color.primary)
                    viewModel.addInterestingSelectedItem("golf")
                } else {
                    // 선택 해제된 경우
                    viewTransparentGolf.isVisible = false
                    Blurry.delete(binding.cvGolf)

                    cvGolf.strokeColor = ContextCompat.getColor(requireContext(), R.color.gray_12)
                    ivLovelyGolf.isVisible = false
                    viewModel.deleteInterestingSelectedItem("golf")
                }
            }

            cvEtc.setOnClickListener {
                val isSelected: Boolean? = viewModel.checkInteresting("etc")
                if (isSelected == false) {
                    // 선택 되었을 경우
                    viewTransparentEtc.isVisible = true
                    Blurry.with(binding.cvEtc.context).radius(6).sampling(2).onto(binding.cvEtc)

                    ivLovelyEtc.isVisible = true
                    cvEtc.strokeColor = ContextCompat.getColor(requireContext(), R.color.primary)
                    viewModel.addInterestingSelectedItem("etc")
                } else {
                    // 선택 해제된 경우
                    viewTransparentEtc.isVisible = false
                    Blurry.delete(binding.cvEtc)

                    cvEtc.strokeColor = ContextCompat.getColor(requireContext(), R.color.gray_12)
                    ivLovelyEtc.isVisible = false
                    viewModel.deleteInterestingSelectedItem("etc")
                }
            }
        }
    }

}