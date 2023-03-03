package com.seunggyu.stitch.ui

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.toSpannable
import androidx.core.view.WindowInsetsControllerCompat
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.seunggyu.stitch.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        uiInit()
        init()
    }

    private fun uiInit() {
        val text = binding.tvPrivacyAgree.text.toString()
        val textServiceStartPoint = text.split("서비스 약관")[0].length
        val textServiceEndPoint = textServiceStartPoint + 6
        val textPrivacyStartPoint = text.split("개인정보 처리방침")[0].length
        val textPrivacyEndPoint = textPrivacyStartPoint + 9

        this.window?.apply {
            this.statusBarColor = Color.TRANSPARENT
            WindowInsetsControllerCompat(this, this.decorView).isAppearanceLightStatusBars = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                setDecorFitsSystemWindows(false)
            } else {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
        }

        binding.tvPrivacyAgree.text.toSpannable().setSpan(object : ClickableSpan() {
            override fun onClick(p0: View) {
                Log.d("MyTag","clicked!")
            }
        }, textServiceStartPoint, textServiceEndPoint, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)

        binding.tvPrivacyAgree.text.toSpannable().setSpan(object : ClickableSpan() {
            override fun onClick(p0: View) {
                Log.d("MyTag","clicked!")
            }
        }, textPrivacyStartPoint, textPrivacyEndPoint, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        binding.tvPrivacyAgree.movementMethod = LinkMovementMethod.getInstance()

    }

    private fun init() {

        with(binding) {
            btnKakaoLogin.setOnClickListener {
                if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@LoginActivity)) {
                    UserApiClient.instance.loginWithKakaoTalk(this@LoginActivity, callback = kakaoCallback)
                } else {
                    UserApiClient.instance.loginWithKakaoAccount(this@LoginActivity, callback = kakaoCallback)
                }
            }
            btnGoogleLogin.setOnClickListener {
                /* TODO : 구글 로그인 기능 구현 */
            }
        }
    }
    internal val kakaoCallback : (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e("kakao result","로그인 실패- $error")
        } else if (token != null) {
            Log.e("kakao result","로그인성공 - 토큰 ${token.accessToken} id토큰 ${token.idToken}")

            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}