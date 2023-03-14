package com.seunggyu.stitch.ui.fragment.newmatch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.seunggyu.stitch.databinding.FragMatchDetailBinding
import com.seunggyu.stitch.databinding.FragMatchSportBinding
import com.seunggyu.stitch.viewModel.CreateNewMatchViewModel

class MatchDetailFragment : Fragment() {
    private val viewModel by activityViewModels<CreateNewMatchViewModel>()

    private val binding by lazy {
        FragMatchDetailBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        init()

        return binding.root
    }

    fun init() {
        with(binding) {

        }
    }

}