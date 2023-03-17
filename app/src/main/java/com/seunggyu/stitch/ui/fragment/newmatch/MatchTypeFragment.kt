package com.seunggyu.stitch.ui.fragment.newmatch

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.seunggyu.stitch.R
import com.seunggyu.stitch.databinding.FragMatchTypeBinding
import com.seunggyu.stitch.viewModel.CreateNewMatchViewModel

class MatchTypeFragment : Fragment() {
    private val viewModel by activityViewModels<CreateNewMatchViewModel>()

    private val binding by lazy {
        FragMatchTypeBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        init()

        return binding.root
    }

    fun init() {
        with(binding) {
            cvNewmatchTypeRegular.setOnClickListener {
                cvNewmatchTypeRegular.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray_11))
                ivMatchRegular.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.primary))
                tvNewmatchTypeRegularTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_02))
                tvNewmatchTypeRegularDescription.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_02))

                cvNewmatchTypeTeach.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black_transparent_01))
                ivMatchTeach.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.gray_09))
                tvNewmatchTypeTeachTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_09))
                tvNewmatchTypeTeachDescription.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_09))

                viewModel.setType("match")
                // 버튼 변경
                nextButtonEnable()
            }

            cvNewmatchTypeTeach.setOnClickListener {
                cvNewmatchTypeRegular.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black_transparent_01))
                ivMatchRegular.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.gray_09))
                tvNewmatchTypeRegularTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_09))
                tvNewmatchTypeRegularDescription.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_09))

                cvNewmatchTypeTeach.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray_11))
                ivMatchTeach.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.primary))
                tvNewmatchTypeTeachTitle.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_02))
                tvNewmatchTypeTeachDescription.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_02))

                viewModel.setType("teach")
                // 버튼 변경
                nextButtonEnable()
            }

            btnNewmatchTypeNext.setOnClickListener {
                viewModel.nextPage()
            }
        }
    }

    private fun nextButtonEnable() {
        with(binding) {
            btnNewmatchTypeNext.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_round)
            btnNewmatchTypeNext.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_12))
            btnNewmatchTypeNext.isEnabled = true
        }
    }

}