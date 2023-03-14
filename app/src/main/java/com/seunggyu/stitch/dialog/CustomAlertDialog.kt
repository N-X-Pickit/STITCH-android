package com.seunggyu.stitch.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.seunggyu.stitch.R
import com.seunggyu.stitch.databinding.DialogCustomAlertBinding

class CustomAlertDialog(
    context: Context,
    private val _title: String,
    private val _description: String,
    private val _negativeText: String,
    private val _positiveText: String
) : Dialog(context) {

    private lateinit var binding: DialogCustomAlertBinding

    private var listener: ((Boolean) -> Unit)? = null

    fun setDialogListener(listener: (Boolean) -> Unit) {
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogCustomAlertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    fun init() {
        with(binding) {
            title = _title
            description = _description
            negativeText = _negativeText
            positiveText = _positiveText

            btnAlertNegative.setOnClickListener {
                listener?.invoke(false)
                dismiss()
            }

            btnAlertPositive.setOnClickListener {
                listener?.invoke(true)
                dismiss()
            }
        }
    }

}