package com.seunggyu.stitch.ui

import AddressSearchViewModel
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MediatorLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.seunggyu.stitch.R
import com.seunggyu.stitch.Util.SnackBarCustom
import com.seunggyu.stitch.adapter.GpsResultRecyclerViewAdapter
import com.seunggyu.stitch.adapter.MyRecyclerViewAdapter
import com.seunggyu.stitch.adapter.SignupProfileListAdapter
import com.seunggyu.stitch.databinding.ActivityAddressBinding
import com.seunggyu.stitch.ui.fragment.*
import com.seunggyu.stitch.viewModel.SignupViewModel
import kotlinx.coroutines.*


class AddressSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddressBinding
    private val viewModel: AddressSearchViewModel by viewModels()

    private val handler = Handler(Looper.getMainLooper())
    private val runnable = Runnable {
        viewModel.geocoding()
    }

    private val gpsRecyclerViewAdapter: GpsResultRecyclerViewAdapter by lazy {
        GpsResultRecyclerViewAdapter(binding.rvGpsResult)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_address)

        uiInit()
        init()
        initRecyclerView()
        initObserver()
    }

    private fun uiInit() {
    }

    private fun init() {

        with(binding) {
            btnAddressBack.setOnClickListener {
                finish()
            }

            etAddressSearch.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val afterText = s.toString()

                    if (afterText.isNotEmpty()) {

                        if (afterText.length > 1 && afterText.last() != '도' && afterText.last() != '시') {
                            Log.e("load", "시x, 도x")
                            handler.removeCallbacks(runnable)
                            handler.postDelayed(runnable, 500)
                            viewModel.inputAddress = afterText
                            Log.e("onTextChanged", afterText)
                        } else {
                            Log.e("clAddressResult", "#5")
                            binding.clAddressResult.isVisible = false
                            binding.clAddressNull.isVisible = true
                        }
                    } else {
                        Log.e("clAddressResult", "#4")
                        binding.clAddressResult.isVisible = false
                        binding.clAddressNull.isVisible = false
                    }
                }
            })

            btnAddressResearch.setOnClickListener {
                etAddressSearch.setText("")
                Log.e("clAddressResult", "#3")
                binding.clAddressNull.isVisible = false
                binding.clAddressResult.isVisible = false

                // 키보드 올리기
                val inputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.showSoftInput(binding.etAddressSearch, 0)
            }
        }

    }

    private fun initObserver() {
        viewModel.addressList.observe(this) {
            it?.let {
                if (it.isNotEmpty()) {
                    Log.e("isDetailAddress", "true")
                    gpsRecyclerViewAdapter.submitList(it)
                    Log.e("it.size", it.size.toString())
                    binding.clAddressResult.isVisible = true
                    binding.clAddressNull.isVisible = false
                    binding.tvAddressResult.text = getString(
                        R.string.address_result_name,
                        binding.etAddressSearch.text
                    )
                } else {
                    Log.e("clAddressResult", "#1")
                    binding.clAddressResult.isVisible = false
                    binding.clAddressNull.isVisible = true
                }
            } ?: run {
                SnackBarCustom.make(
                    binding.clAddressParent,
                    getString(R.string.snackbar_network_error)
                )
                    .show()
            }
        }
        viewModel.addressNear.observe(this) {
            it?.let {
                binding.clAddressResult.isVisible = true
                binding.clAddressNull.isVisible = false
                binding.tvAddressResult.text =
                    getString(R.string.address_result_near, viewModel.addressNear)
            } ?: run {
                SnackBarCustom.make(
                    binding.clAddressParent,
                    getString(R.string.snackbar_network_error)
                )
                    .show()
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvGpsResult.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = gpsRecyclerViewAdapter
            Log.e("RecyclerView init", "successed")
        }
    }

    override fun onBackPressed() {
        finish()
    }
}