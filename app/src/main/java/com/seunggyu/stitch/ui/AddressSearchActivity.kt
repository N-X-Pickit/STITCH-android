package com.seunggyu.stitch.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MediatorLiveData
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.seunggyu.stitch.R
import com.seunggyu.stitch.adapter.SignupProfileListAdapter
import com.seunggyu.stitch.databinding.ActivityAddressBinding
import com.seunggyu.stitch.databinding.ActivitySignupBinding
import com.seunggyu.stitch.ui.fragment.*
import com.seunggyu.stitch.viewModel.SignupViewModel
import kotlinx.coroutines.*


class AddressSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_address)

        uiInit()
        init()
    }

    private fun uiInit() {
    }

    private fun init() {

        with(binding) {
           btnAddressBack.setOnClickListener {
               finish()
           }
        }

    }

    override fun onBackPressed() {
        finish()
    }
}