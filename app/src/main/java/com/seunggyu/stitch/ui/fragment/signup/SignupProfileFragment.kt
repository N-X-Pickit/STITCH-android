package com.seunggyu.stitch.ui.fragment.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.seunggyu.stitch.adapter.SignupProfileListAdapter
import com.seunggyu.stitch.databinding.FragProfileBinding
import com.seunggyu.stitch.viewModel.SignupViewModel

class SignupProfileFragment : Fragment() {
    private val viewModel by activityViewModels<SignupViewModel>()

    private val binding by lazy {
        FragProfileBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        with(binding) {
            rvProfileItem.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = SignupProfileListAdapter(viewModel.items, viewModel = viewModel)
//                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }
        }

        return binding.root
    }
}