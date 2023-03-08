package com.seunggyu.stitch.Util

import android.content.res.Resources
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_SLIDE
import com.google.android.material.snackbar.Snackbar
import com.seunggyu.stitch.R
import com.seunggyu.stitch.databinding.SnackbarCustomBinding

class SnackBarCustom(view: View, private val message: String) {

    companion object {

        fun make(view: View, message: String) = SnackBarCustom(view, message)
    }

    private val context = view.context
    private val snackbar = Snackbar.make(view, "", 5000)
    private val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout

    private val inflater = LayoutInflater.from(context)
    private val snackbarBinding: SnackbarCustomBinding = DataBindingUtil.inflate(inflater, R.layout.snackbar_custom, null, false)

    init {
        initView()
        initData()
    }

    private fun initView() {
        with(snackbarLayout) {
            removeAllViews()
            val mlayoutParams = snackbarLayout.layoutParams as ViewGroup.MarginLayoutParams
            mlayoutParams.setMargins(16.dpToPx(), 0, 16.dpToPx(), 24.dpToPx())
            layoutParams = mlayoutParams
            setPadding(0, 0, 0, 0)
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
            addView(snackbarBinding.root, 0)
        }
    }

    private fun initData() {
        snackbarBinding.tvSnackbarMsg.text = message
    }

    fun show() {
        snackbar.animationMode = ANIMATION_MODE_SLIDE
        snackbar.show()
    }

    fun Int.dpToPx(): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, this.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()
    }
}