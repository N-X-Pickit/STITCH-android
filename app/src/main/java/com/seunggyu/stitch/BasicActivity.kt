package com.seunggyu.stitch

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

open class BasicActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 이 화면은 왼쪽에서 오른쪽으로 슬라이딩 하면서 켜짐
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_none)
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    }

    override fun finish() {
        super.finish()
        if (isFinishing) {
            // 이 화면은 왼쪽에서 오른쪽으로 슬라이딩 하면서 사라잠
            overridePendingTransition(R.anim.anim_none, R.anim.anim_slide_out_right)
        }
    }
}