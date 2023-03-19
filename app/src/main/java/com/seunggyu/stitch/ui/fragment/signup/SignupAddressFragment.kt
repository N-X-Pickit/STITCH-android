package com.seunggyu.stitch.ui.fragment.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val data = result.data
                val address = data!!.getStringExtra("key")
                binding.tvSignup3InputHome.text = address
                viewModel.setLocation(address = address!!)
            }
        }

        binding.tvSignup3InputHome.setOnClickListener {
            activityResultLauncher.launch(Intent(requireActivity(), AddressSearchActivity::class.java))

        }
    }
    fun init() {
        with(viewModel) {
            inputHome.observe(viewLifecycleOwner) {
                it?.let {
                    binding.tvSignup3InputHome.text = it
                }
            }

            location.observe(requireActivity()) {
                it?.let {
                    Log.e("location submitted", it)
                    ableButton()
                }
            }
        }

        with(binding) {
//            tvSignup3InputHome.setOnClickListener {
//                val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//                    if (result.resultCode == AppCompatActivity.RESULT_OK) {
//                        val data = result.data
//                        val address = data!!.getStringExtra("key")
//                        viewModel.setLocation(address = address!!)
//                    }
//                }
//                activityResultLauncher.launch(Intent(requireActivity(), AddressSearchActivity::class.java))
////                startActivity(Intent(context, AddressSearchActivity::class.java))
//            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}