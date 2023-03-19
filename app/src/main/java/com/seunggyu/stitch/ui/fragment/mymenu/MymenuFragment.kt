package com.seunggyu.stitch.ui.fragment.mymenu

import android.content.res.Resources
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

import com.seunggyu.stitch.R
import com.seunggyu.stitch.databinding.FragMainMymenuBinding

class MymenuFragment : Fragment() {
    private lateinit var binding: FragMainMymenuBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_main_mymenu, container, false)
        initClickListener()
        initView()
        initGroup()
        initInsetMargin()
        initObserver()
        return binding.root
    }

    private fun initView() {

    }

    private fun initObserver() {

    }

    private fun initGroup() {

    }

    private fun initClickListener() {

    }

    private fun initInsetMargin() = with(binding) {
        ViewCompat.setOnApplyWindowInsetsListener(svMymenu) { v: View, insets: WindowInsetsCompat ->
            val params = v.layoutParams as ViewGroup.MarginLayoutParams
            params.bottomMargin = insets.systemWindowInsetBottom
            clMymenu.layoutParams =
                (clMymenu.layoutParams as ViewGroup.MarginLayoutParams).apply {
                    setMargins(16.dpToPx(), insets.systemWindowInsetTop, 16.dpToPx(), 0)
                }


            insets.consumeSystemWindowInsets()
        }
    }
    fun Int.dpToPx(): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, this.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()
    }
}