package com.seunggyu.stitch.ui.fragment.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.seunggyu.stitch.R
import com.seunggyu.stitch.databinding.FragNicknameBinding
import com.seunggyu.stitch.viewModel.SignupViewModel

class SignupNameFragment : Fragment() {
    private var _binding: FragNicknameBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<SignupViewModel>()
    private var duplicationFlag = false
    private var lengthFlag = false
    private var availableFlag = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragNicknameBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    fun init() {
        with(binding) {
            etSignupNickname.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    // 변경 전
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // 변경 중
                }

                override fun afterTextChanged(s: Editable?) {
                    // 변경 후
                    lengthFlag = lengthCheck(s.toString())
                    availableFlag = availableCheck(s.toString())
                    if(!availableFlag) {
                        tvSignupError.visibility = View.VISIBLE
                        tvSignupError.text = "닉네임은 띄어쓰기 없이 한글, 영문, 숫자만 가능해요."
                    }

                    if(lengthFlag && availableFlag) {
                        viewModel.ableButton()
                        viewModel.setInputName(binding.etSignupNickname.text.toString())
                        binding.ivSignupNicknameError.visibility = View.GONE
                        binding.tvSignupError.visibility = View.GONE
                        Log.e("Tag", "button able")
                    }else{
                        if(s.toString().isNotEmpty()) binding.ivSignupNicknameError.visibility = View.VISIBLE
                        else binding.ivSignupNicknameError.visibility = View.GONE
                        viewModel.disableButton()
                        Log.e("Tag", "button disable")
                    }
                }
            })
            ivSignupNicknameClose.setOnClickListener {
                etSignupNickname.setText("")
                tvSignupError.visibility = View.GONE
            }
        }
    }

    fun lengthCheck(s: String) : Boolean{
        binding.tvSignup1TextLenght.text = "${s?.length} / 10"

        when(s.length) {
            0 -> {
                binding.tvSignup1TextLenght.visibility = View.GONE
                binding.ivSignupNicknameClose.visibility = View.GONE
                lengthFlag = false
                return false
            }
            in 1..10 -> {
                binding.tvSignup1TextLenght.visibility = View.VISIBLE
                binding.tvSignup1TextLenght.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_09))
                lengthFlag = true
                binding.ivSignupNicknameClose.visibility = View.VISIBLE
                return true
            }
            else -> {
                binding.tvSignup1TextLenght.setTextColor(ContextCompat.getColor(requireContext(), R.color.error))
                binding.tvSignupError.text = "제한된 글자 수를 초과했습니다."
                binding.tvSignupError.visibility = View.VISIBLE
                lengthFlag = false
                return false
            }
        }
    }

    fun availableCheck(s: String) : Boolean {
        return "^[가-힣a-zA-Z\\d]*$".toRegex().matches(s)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}