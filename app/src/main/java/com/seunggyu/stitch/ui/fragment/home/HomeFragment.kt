package com.seunggyu.stitch.ui.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.seunggyu.stitch.GlobalApplication

import com.seunggyu.stitch.R
import com.seunggyu.stitch.Util.SnackBarCustom
import com.seunggyu.stitch.databinding.FragMainHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragMainHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_main_home, container, false)
        initClickListener()
        initObserver()
        initData()
        initView()

        return binding.root
    }

    private fun initView() {

    }

    private fun initObserver() {

    }

    private fun initClickListener() {

    }

    private fun initData() {
        GlobalApplication.bannerData.let {
            if (it != null) {
                for(index in it.indices) {
                    Log.e("imageurl", it[index].imageUrl.toString())
                    Log.e("title", it[index].title.toString())
                }
            }
        }
    }
}