package com.seunggyu.stitch.ui.fragment.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.seunggyu.stitch.data.RetrofitApi
import com.seunggyu.stitch.data.model.request.SignupRequest
import com.seunggyu.stitch.databinding.FragAddressBinding
import com.seunggyu.stitch.ui.AddressSearchActivity
import com.seunggyu.stitch.viewModel.SignupViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupAddressFragment : Fragment() {
    private var _binding: FragAddressBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<SignupViewModel>()
    private var availableFlag = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragAddressBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    fun init() {
        with(viewModel) {
            inputHome.observe(viewLifecycleOwner) {
                it?.let {
                    binding.tvSignup3InputHome.text = it
                }
            }
        }

        with(binding) {
            tvSignup3InputHome.setOnClickListener {
                startActivity(Intent(context, AddressSearchActivity::class.java))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}