package com.seunggyu.stitch.ui.fragment.signup

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.seunggyu.stitch.R
import com.seunggyu.stitch.databinding.FragSignupSuccessBinding
import com.seunggyu.stitch.viewModel.SignupViewModel

class SignupSuccessFragment : Fragment() {
    private val viewModel by activityViewModels<SignupViewModel>()

    private val binding by lazy {
        FragSignupSuccessBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        uiInit()

        return binding.root
    }

    fun uiInit() {
        val primaryColor = ContextCompat.getColor(requireContext(), R.color.primary)
        val text = binding.tvSignupSuccessTop.text
        val spannableString = SpannableString(text)
        // STITCH 부분에 대해 색상을 변경
        val start = text.indexOf("STITCH")
        val end = start + "STITCH".length

        spannableString.setSpan(
            ForegroundColorSpan(primaryColor),
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // TextView에 SpannableString을 설정합니다.
        binding.tvSignupSuccessTop.text = spannableString



    }
}