package com.seunggyu.stitch.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.seunggyu.stitch.adapter.SignupProfileListAdapter
import com.seunggyu.stitch.databinding.FragSignup2Binding
import com.seunggyu.stitch.databinding.FragSignup4Binding
import com.seunggyu.stitch.viewModel.SignupViewModel

class SignupCategoryFragment : Fragment() {
    private val viewModel by viewModels<SignupViewModel>()

    private val binding by lazy {
        FragSignup4Binding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        with(binding) {

        }

        return binding.root
    }
}