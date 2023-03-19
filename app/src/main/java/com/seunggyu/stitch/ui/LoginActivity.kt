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
import androidx.core.view.isVisible
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.seunggyu.stitch.GlobalApplication
import com.seunggyu.stitch.MainActivity
import com.seunggyu.stitch.R
import com.seunggyu.stitch.Util.SnackBarCustom
import com.seunggyu.stitch.data.RetrofitApi
import com.seunggyu.stitch.data.model.request.SignupRequest
import com.seunggyu.stitch.databinding.ActivityLoginBinding
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
            viewModel.setLoginNickName(account?.displayName.toString())
            viewModel.setLoginImageUrl(account?.photoUrl.toString())
            viewModel.setLoginId(account?.id.toString())

            binding.progressLoadingLogin.isVisible = true
            startSignupProcess()
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
                    viewModel.setLoginNickName(user.kakaoAccount?.profile?.nickname.toString())
                    viewModel.setLoginId(user.id.toString())
                    viewModel.setLoginImageUrl(user.kakaoAccount?.profile?.profileImageUrl.toString())

                    binding.progressLoadingLogin.isVisible = true
                    startSignupProcess()
                }
            }

        }
    }

    fun startSignupProcess() {
        isMember(viewModel.loginId.value.toString(), ::isMemberCallback)
    }

    fun isMember(id: String, callback: (Boolean, String) -> Unit) {
        val service = RetrofitApi.retrofitService

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.isMember(id) // true / false

            withContext(Dispatchers.Main) {
                if (response) {
                    callback(true, id)
                } else {
                    callback(false, "0")
                }
            }
        }
    }

    fun isMemberCallback(_result: Boolean, id: String) {
        if(_result) { // 회원가입이 되어 있는 경우
            val service = RetrofitApi.retrofitService

            CoroutineScope(Dispatchers.IO).launch {
                val response = service.getUserData(id) // true / false

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        result?.let {
                            Log.e("result>>>>>>>", result.toString())

                            setPreferences(it.id, it.name, it.imageUrl, it.location,
                            it.sports, it.token, it.introduce, it.type)
                        }
                    } else {
                        Log.e("TAG", response.code().toString())
                        SnackBarCustom.make(binding.root, getString(R.string.snackbar_network_error))
                            .show()
                    }
                }
            }

        } else { // 회원가입이 되어 있지 않은 경우
            binding.progressLoadingLogin.isVisible = false
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            intent.putExtra("LOGIN_ID", viewModel.loginId.value.toString())
            intent.putExtra("LOGIN_NICKNAME", viewModel.loginNickName.value.toString())
            intent.putExtra("LOGIN_IMAGEURL", viewModel.loginImageUrl.value.toString())
            startActivity(intent)
        }
    }

    private fun setPreferences(id: String, name: String, imageUrl: String,
                               location: String, sports: List<String>, token: String,
                               introduce: String, type: String) {
        GlobalApplication.prefs.setString("userId", id)
        GlobalApplication.prefs.setString("name", name)
        GlobalApplication.prefs.setString("imageUrl", imageUrl)
        GlobalApplication.prefs.setString("location", location)
        GlobalApplication.prefs.setStringList("sports", sports)
        GlobalApplication.prefs.setString("token", token)
        GlobalApplication.prefs.setString("introduce", introduce)
        GlobalApplication.prefs.setString("type", type)

        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        binding.progressLoadingLogin.isVisible = false
        startActivity(intent)
        finish()
    }
}