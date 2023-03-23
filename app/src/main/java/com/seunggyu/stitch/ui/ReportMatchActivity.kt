package com.seunggyu.stitch.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.seunggyu.stitch.BasicActivity
import com.seunggyu.stitch.R
import com.seunggyu.stitch.Util.SnackBarCustom
import com.seunggyu.stitch.data.RetrofitApi
import com.seunggyu.stitch.data.model.request.ReportRequest
import com.seunggyu.stitch.databinding.ActivityReportBinding
import com.seunggyu.stitch.dialog.CustomAlertDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReportMatchActivity : BasicActivity() {
    private lateinit var binding: ActivityReportBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_report)

        init()
        initClickListener()

    }

    private fun init() {
        intent.getStringExtra("reporterId")
        intent.getStringExtra("matchId")


        with(binding) {
            btnReportConfirm.setOnClickListener {
                report()

            }

        }
    }

    private fun report() {
        val service = RetrofitApi.retrofitService

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.report(
                ReportRequest(matchId = intent.getStringExtra("matchId"),
                    memberId = intent.getStringExtra("memberId"),
                    reason = binding.etReport.text.toString(),
                    reporterId = intent.getStringExtra("reporterId")
                )
            )

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val result = response.body()
                    result?.let {
                        Log.e("result>>>>>>>", result.toString())
                        SnackBarCustom.make(binding.clAddressParent, "신고가 정상적으로 접수되었습니다.").show()
                        finish()
                    }
                } else {
                    SnackBarCustom.make(binding.clAddressParent, getString(R.string.snackbar_network_error)).show()
                }
            }
        }
    }

    private fun initClickListener() {
        binding.etReport.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString().length > 500) {
                    binding.tvReportError.visibility = View.VISIBLE
                    binding.dividerReport.dividerColor = ContextCompat.getColor(this@ReportMatchActivity, R.color.error)
                    binding.tvReportLength.setTextColor(ContextCompat.getColor(this@ReportMatchActivity,R.color.error))
                } else {
                    binding.tvReportError.visibility = View.GONE
                    binding.tvReportLength.visibility = View.VISIBLE
                    binding.tvReportLength.setTextColor(ContextCompat.getColor(this@ReportMatchActivity,R.color.gray_09))
                    binding.dividerReport.dividerColor = ContextCompat.getColor(this@ReportMatchActivity, R.color.gray_07)
                }

                if(s.toString().isEmpty())  {
                    binding.tvReportError.visibility = View.GONE
                    binding.btnReportConfirm.setBackgroundResource(R.drawable.button_round_disabled)
                    binding.btnReportConfirm.setTextColor(ContextCompat.getColor(this@ReportMatchActivity, R.color.gray_07))
                    binding.btnReportConfirm.isEnabled = false
                } else {
                    binding.btnReportConfirm.setBackgroundResource(R.drawable.button_round)
                    binding.btnReportConfirm.setTextColor(ContextCompat.getColor(this@ReportMatchActivity, R.color.gray_12))
                    binding.btnReportConfirm.isEnabled = true
                }


                binding.tvReportError.text = getString(R.string.newmatch_detail_input_length, s.toString().length.toString(), "500")
            }
        })
    }

    fun showCloseDialog() {
        val title = "신고를 그만두시겠어요?"
        val description = "작성된 내용은 사라집니다."
        val negativeText = getString(R.string.alert_quit)
        val positiveText = getString(R.string.alert_continue)
        val color = "primary"
        val dialog = CustomAlertDialog(
            this@ReportMatchActivity,
            title,
            description,
            negativeText,
            positiveText,
            color
        )
        dialog.setDialogListener { okClicked ->
            if (!okClicked) {
                finish()
            }
        }

        // 다이얼로그 테두리 외곽 부분 투명하게 설정
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

        dialog.show()
    }

    override fun onBackPressed() {
        showCloseDialog()
    }
}