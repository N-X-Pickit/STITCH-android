package com.seunggyu.stitch.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.seunggyu.stitch.databinding.ActivitySignupBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private var signupPageIndicator = 0  // 현재 보여지고 있는 페이지
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        uiInit()
        init()
    }

    private fun uiInit() {
        signupPageIndicator = 1
    }

    private fun init() {
        with(binding) {

            // 다음버튼 클릭시 progressbar 25씩 증가
            btnSignupNext.setOnClickListener {
                Toast.makeText(this@SignupActivity, "다음 버튼 클릭", Toast.LENGTH_SHORT).show()
                if (progressSignup.progress <= 100) {
                    CoroutineScope(Dispatchers.Main).launch {
                        repeat(25) {
                            progressSignup.progress += 1

                            delay(10)

                        }
                    }
                }
            }

            // 뒤로가기 버튼 클릭시 progressbar 25씩 감소
            btnSignupBack.setOnClickListener {
                if (progressSignup.progress <= 25) {
                    finish()
                }
                CoroutineScope(Dispatchers.Main).launch {
                    repeat(25) {
                        progressSignup.progress -= 1

                        delay(10)

                    }
                }
            }
        }
    }
}