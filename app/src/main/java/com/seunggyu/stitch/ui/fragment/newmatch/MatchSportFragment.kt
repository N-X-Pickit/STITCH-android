package com.seunggyu.stitch.ui.fragment.newmatch

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.seunggyu.stitch.databinding.FragMatchSportBinding
import com.seunggyu.stitch.viewModel.CreateNewMatchViewModel

class MatchSportFragment : Fragment() {
    private val viewModel by activityViewModels<CreateNewMatchViewModel>()

    private val binding by lazy {
        FragMatchSportBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        init()

        return binding.root
    }

    fun init() {
        with(binding) {
            layoutViewModel = viewModel
//            cvTennis.setOnClickListener {
//                viewModel.setSportsType("테니스")
//                Log.e("dasdasd","clicked")
//            }
//            cvBadminton.setOnClickListener {
//                viewModel.setSportsType("배드민턴")
//            }
//            cvPingpong.setOnClickListener {
//                viewModel.setSportsType("탁구")
//            }
//            cvSoccer.setOnClickListener {
//                viewModel.setSportsType("축구")
//            }
//            cvRunning.setOnClickListener {
//                viewModel.setSportsType("런닝")
//            }
//            cvMountain.setOnClickListener {
//                viewModel.setSportsType("등산")
//            }
//            cvHealth.setOnClickListener {
//                viewModel.setSportsType("헬스")
//            }
//            cvBaseball.setOnClickListener {
//                viewModel.setSportsType("농구")
//            }
//            cvBaseball.setOnClickListener {
//                viewModel.setSportsType("야구")
//            }
//            cvGolf.setOnClickListener {
//                viewModel.setSportsType("골프")
//            }
//            cvEtc.setOnClickListener {
//                viewModel.setSportsType("기타")
//            }
        }
    }

}