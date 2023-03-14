package com.seunggyu.stitch.ui

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.toSpannable
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.seunggyu.stitch.databinding.ActivityLoginBinding
import com.seunggyu.stitch.data.RetrofitApi
import com.seunggyu.stitch.data.model.request.SignupRequest
import com.seunggyu.stitch.viewModel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var GoogleSignResultLauncher: ActivityResultLauncher<Intent>
    private val viewModel: LoginViewModel by viewModels()

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
                Log.d("MyTag", "clicked!")
            }
        }, textServiceStartPoint, textServiceEndPoint, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)

        binding.tvPrivacyAgree.text.toSpannable().setSpan(object : ClickableSpan() {
            override fun onClick(p0: View) {
                Log.d("MyTag", "clicked!")
            }
        }, textPrivacyStartPoint, textPrivacyEndPoint, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        binding.tvPrivacyAgree.movementMethod = LinkMovementMethod.getInstance()

    }

    private fun init() {
        // 구글 로그인 로직
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){ result ->
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)
        }

        with(binding) {
            btnKakaoLogin.setOnClickListener {
                if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@LoginActivity)) {
                    UserApiClient.instance.loginWithKakaoTalk(
                        this@LoginActivity,
                        callback = kakaoCallback
                    )
                } else {
                    UserApiClient.instance.loginWithKakaoAccount(
                        this@LoginActivity,
                        callback = kakaoCallback
                    )
                }
            }
            btnGoogleLogin.setOnClickListener {
                var signIntent: Intent = mGoogleSignInClient.getSignInIntent()
                GoogleSignResultLauncher.launch(signIntent)
            }
        }
    }

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            viewModel.setGoogleEmail(account?.email.toString())
            viewModel.setGoogleToken(account?.idToken.toString())
            viewModel.setGoogleTokenAuth(account?.serverAuthCode.toString())
            viewModel.setGoogleNickName(account?.displayName.toString())
            viewModel.setGooglePhotoUrl(account?.photoUrl.toString())
            viewModel.setGoogleId(account?.id.toString())

            Log.e("Google account Email",viewModel.getGoogleEmail())
            Log.e("Google account Token",viewModel.getGoogleToken())
            Log.e("Google account TokenAuth", viewModel.getGoogleTokenAuth())
            Log.e("Google account NickName", viewModel.getGoogleNickName())
            Log.e("Google account PhotoUrl", viewModel.getGooglePhotoUrl())
            Log.e("Google account Id", viewModel.getGoogleId())
        } catch (e: ApiException){
            Log.e("Google account","signInResult:failed Code = " + e.statusCode)
        }
    }

    private val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e("kakao result", "로그인 실패- $error")
        } else if (token != null) {
            Log.e("kakao result", "로그인성공 - 토큰 ${token.accessToken} id토큰 ${token.idToken} ")
            UserApiClient.instance.me { user, mError ->
                if (mError != null) {
                    Log.e(TAG, "사용자 정보 요청 실패 $error")
                } else if (user != null) {
                    Log.e(TAG, "사용자 정보 요청 성공 : $user")
                    viewModel.setKakaoNickName(user.kakaoAccount?.profile?.nickname.toString())
                    viewModel.setKakaoId(user.id.toString())
                    viewModel.setKakaoPhotoUrl(user.kakaoAccount?.profile?.profileImageUrl.toString())

                    retrofitWork()
                    Log.e("kakaoId", viewModel.getKakaoId())
                    Log.e("kakaoNickName", viewModel.getKakaoNickName())
                    Log.e("kakaoPhotoUrl", viewModel.getKakaoPhotoUrl())
                }
            }
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun retrofitWork() {
        val service = RetrofitApi.retrofitService

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.signup(SignupRequest(id = "1121127211", type = "member", location = "서울시 대한구 민국동", imageUrl = "www.abcd.com", name = "홍길동", sports = listOf("야구", "축구", "등산")))

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val result = response.body()
                    result?.let {
                        Log.e("result id", result.id.toString())
                        Log.e("result type", result.type.toString())
                        Log.e("result location", result.location.toString())
                        Log.e("result imageUrl", result.imageUrl.toString())
                        Log.e("result name", result.name.toString())
                        Log.e("result sports", result.sports.toString())
                    }
                } else {
                    Log.e("TAG", response.code().toString())
                }
            }
        }
    }
}