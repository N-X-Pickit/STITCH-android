package com.seunggyu.stitch.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.seunggyu.stitch.BasicActivity
import com.seunggyu.stitch.GlobalApplication
import com.seunggyu.stitch.MainActivity
import com.seunggyu.stitch.R
import com.seunggyu.stitch.Util.SnackBarCustom
import com.seunggyu.stitch.data.RetrofitApi
import com.seunggyu.stitch.data.model.request.SignupRequest
import com.seunggyu.stitch.databinding.ActivitySignupBinding
import com.seunggyu.stitch.ui.fragment.*
import com.seunggyu.stitch.ui.fragment.signup.SignupAddressFragment
import com.seunggyu.stitch.ui.fragment.signup.SignupInterestFragment
import com.seunggyu.stitch.ui.fragment.signup.SignupSuccessFragment
import com.seunggyu.stitch.viewModel.SignupViewModel
import kotlinx.coroutines.*


class SignupActivity : BasicActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var viewPager: ViewPager2

    private var signupPageIndicator = 0  // 현재 보여지고 있는 페이지

    private val viewModel: SignupViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)

        uiInit()
        init()

        val pagerAdapter = SignupViewPagerAdapter(this)
        viewPager.adapter = pagerAdapter
        // 뷰페이저 스와이프 막기 -> 다음, 이전 버튼으로만 이동 가능
        viewPager.isUserInputEnabled = false
    }

    private fun uiInit() {
        signupPageIndicator = 1
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init() {


        with(binding) {
            viewPager = vpSignup
            btnSignupNext.setOnClickListener {
//                viewModel.nextPage()
                viewModel.disableButton()
                if (viewPager.currentItem < 1) {
                    Log.e("viewPager.currentItem111", viewPager.currentItem.toString())

                    viewPager.currentItem = viewPager.currentItem + 1
                    viewModel.nextPage()
                    Log.e("viewPager.currentItem2222", viewPager.currentItem.toString())
//                    viewModel.currentPage.value?.plus(1)
                }
                else if(viewPager.currentItem == 1) {
                    Log.e("viewPager.currentItem", viewPager.currentItem.toString())
                    binding.progressLoadingSignup.isVisible = true
                    signupRequest()
                    Log.e("signupRequest","in progress")
                    binding.btnSignupNext.isEnabled = false
                }
            }

            // 뒤로가기 버튼 클릭
            btnSignupBack.setOnClickListener {
                if ((viewModel.currentPage.value ?: 1) == 1 || (viewModel.currentPage.value ?: 3) == 3) finish()
                else viewModel.currentPage.value?.minus(1)

                val currentItem = viewPager.currentItem
                if (currentItem > 0) {
                    viewPager.currentItem = currentItem - 1
                    viewModel.prevPage()
                }
            }

            // 마지막 시작하기 버튼 터치 리스너
            btnSignupSuccess.setOnTouchListener { view, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        // 버튼을 터치하면 drawable 변경
                        view.isPressed = true
                        true
                    }
                    MotionEvent.ACTION_UP -> {
                        // 버튼을 떼면 drawable 변경하고 기능 실행
                        view.isPressed = false


                        startActivity(Intent(this@SignupActivity, MainActivity::class.java))
                        finishAffinity()
                        true
                    }
                    else -> false
                }
            }
        }

        with(viewModel) {

            val loginId = intent.getStringExtra("LOGIN_ID").toString()
            val loginNickName = intent.getStringExtra("LOGIN_NICKNAME").toString()
            val loginImageUrl = intent.getStringExtra("LOGIN_IMAGEURL").toString()
            Log.e("LOGIN_ID", loginId)
            Log.e("LOGIN_NICKNAME", loginNickName)
            Log.e("LOGIN_IMAGEURL", loginImageUrl)
            intent.getStringExtra("LOGIN_ID")?.let { setLoginId(it) }
            intent.getStringExtra("LOGIN_NICKNAME")?.let { setLoginNickName(it) }
            intent.getStringExtra("LOGIN_IMAGEURL")?.let { setLoginImageUrl(it) }



            currentPage.observe(this@SignupActivity) {
                it?.let {
                    Log.e("CurrentPage", currentPage.value.toString())
                    binding.btnSignupNext.visibility = View.VISIBLE
                    binding.btnSignupSuccess.visibility = View.GONE
                    checkButtonAvailable(it-1) // page = currentPage -1
                    when (it) {
                        3 -> {
                            binding.topText = getString(R.string.signup_success)

                            binding.btnSignupNext.visibility = View.GONE
                            binding.btnSignupSuccess.visibility = View.VISIBLE
                            binding.btnSignupBack.visibility = View.GONE
                            binding.topText = getString(R.string.signup)
                        }
                        1 -> {
                            Log.e("ViewModel's currentPage", it.toString())
                            binding.topText = getString(R.string.signup)
                        }
                        2 -> {
                            Log.e("ViewModel's currentPage", it.toString())
                        }
//                        3 -> {
//                            Log.e("ViewModel's currentPage", it.toString())
//                            Log.e("ViewModel's inputHome", inputHome.value.toString())
//
//                            // currentPage가 3일 때만 inputHome LiveData를 observe
//                            val mediatorLiveData = MediatorLiveData<String>()
//                            mediatorLiveData.addSource(viewModel.inputHome) {
//                                // inputHome LiveData의 값이 변경될 때마다 실행되는 코드
//                                if (inputHome.value == "") disableButton()
//                                else ableButton()
//                            }
//                            mediatorLiveData.observe(this@SignupActivity) { /* NO-OP */ }
//                        }
//                        4 -> {
//                            Log.e("ViewModel's currentPage", it.toString())
//                            binding.topText = getString(R.string.signup)
//                            checkButtonAvailable(4)
//
//                        }
                    }
                }
            }

            interestingSelectedItem.observe(this@SignupActivity) {
                it?.let {
                    checkButtonAvailable(0)
                    Log.e("interestingSelectedItem","observed")
                }
            }

            nextButtonAvailable.observe(this@SignupActivity) {
                it?.let {
                    when (it) {
                        true -> {
                            binding.btnSignupNext.background = ContextCompat.getDrawable(
                                this@SignupActivity,
                                R.drawable.button_round
                            )
                            binding.btnSignupNext.setTextColor(
                                ContextCompat.getColor(
                                    this@SignupActivity,
                                    R.color.gray_12
                                )
                            )
                            binding.btnSignupNext.isEnabled = true
                        }
                        else -> {
                            binding.btnSignupNext.background = ContextCompat.getDrawable(
                                this@SignupActivity,
                                R.drawable.button_round_disabled
                            )
                            binding.btnSignupNext.setTextColor(
                                ContextCompat.getColor(
                                    this@SignupActivity,
                                    R.color.gray_07
                                )
                            )
                            binding.btnSignupNext.isEnabled = false
                        }
                    }
                }
            }
        }

    }

    fun checkButtonAvailable(page: Int) {
        with(viewModel) {
            when (page) {
                0 -> {
                    if(interestingSelectedItem.value?.size!! >= 3) {
                        Log.e("interestingSelectedItem.value?.size!!", interestingSelectedItem.value?.size!!.toString())
                        ableButton()
                        return
                    }
                }

                1 -> {
                    if(inputHome.value?.length!! > 0) {
                        ableButton()
                        return
                    }
                }
            }
            disableButton()
        }
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0 || viewPager.currentItem == 2) {
            super.onBackPressed()
            // 첫번째 (닉네임 입력 화면) 이거나 마지막 (회원가입 완료 화면) 일 경우 activity 종료
            finish()
        } else {
            // 다른 경우 viewPager 이전 페이지로 이동
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    private inner class SignupViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        // 1. ViewPager2에 연결할 Fragment 들을 생성
        val fragmentList = listOf<Fragment>(
            SignupInterestFragment(),
            SignupAddressFragment(),
            SignupSuccessFragment()
        )

        // 2. ViesPager2에서 노출시킬 Fragment 의 갯수 설정
        override fun getItemCount(): Int {
            return fragmentList.size
        }

        // 3. ViewPager2의 각 페이지에서 노출할 Fragment 설정
        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
        }
    }

    private fun signupRequest() {
        val service = RetrofitApi.retrofitService

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.signup(SignupRequest(id = viewModel.loginId,
                location = viewModel.location.value!!.toString(),
                imageUrl = viewModel.loginImageUrl,
                name = viewModel.loginNickName,
                sports = viewModel.interestingSelectedItem.value!!.toList(),
                introduce = getString(R.string.signup_first_introduce),
                type = "member",
            ))

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val result = response.body()
                    result?.let {
                        Log.e("result>>>>>>>", result.toString())
                        viewModel.setToken(it.token.toString())
                        viewModel.setType(it.type.toString())

                        binding.progressLoadingSignup.isVisible = false
                        viewModel.nextPage()
                        viewPager.currentItem = viewPager.currentItem + 1

                        setPreferences()
                    }
                } else {
                    Log.e("TAG", response.code().toString())
                    SnackBarCustom.make(binding.vpSignup, getString(R.string.snackbar_network_error))
                        .show()
                    binding.btnSignupNext.isEnabled = true
                }
            }
        }
    }

    private fun setPreferences() {
        GlobalApplication.prefs.setString("userId", viewModel.loginId)
        GlobalApplication.prefs.setString("name", viewModel.loginNickName)
        GlobalApplication.prefs.setString("imageUrl", viewModel.loginImageUrl)
        GlobalApplication.prefs.setString("location", viewModel.location.value.toString())
        GlobalApplication.prefs.setStringList("sports", viewModel.interestingSelectedItem.value!!.toList())
        GlobalApplication.prefs.setString("token", viewModel.token)
        GlobalApplication.prefs.setString("introduce", getString(R.string.signup_first_introduce))
        GlobalApplication.prefs.setString("type", viewModel.type)
    }

}