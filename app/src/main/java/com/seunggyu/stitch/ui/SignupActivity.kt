package com.seunggyu.stitch.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MediatorLiveData
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.seunggyu.stitch.R
import com.seunggyu.stitch.adapter.SignupProfileListAdapter
import com.seunggyu.stitch.databinding.ActivitySignupBinding
import com.seunggyu.stitch.ui.fragment.*
import com.seunggyu.stitch.viewModel.SignupViewModel
import kotlinx.coroutines.*


class SignupActivity : FragmentActivity() {
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
                viewModel.nextPage()
                val currentItem = viewPager.currentItem
                when (currentItem) {
                    0 -> {
                        Log.e("Viewmodel's InputName", viewModel.inputName)
                    }
                }
                if (currentItem < 5) {
                    checkButtonAvailable(currentItem)
                    viewPager.currentItem = currentItem + 1
                }
            }

            // 뒤로가기 버튼 클릭시 progressbar 25씩 감소
            btnSignupBack.setOnClickListener {
                if ((viewModel.currentPage.value ?: 1) == 1) finish()
                else viewModel.prevPage()

                val currentItem = viewPager.currentItem
                if (currentItem > 0) {
                    viewPager.currentItem = currentItem + -1
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
                        Toast.makeText(this@SignupActivity, "터치 떼짐", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }
        }

        with(viewModel) {
            // 프로그레스바 상태
            progress.observe(this@SignupActivity) {
                it?.let {
                    binding.progress = it
                }
            }

            currentPage.observe(this@SignupActivity) {
                it?.let {
                    binding.btnSignupNext.visibility = View.VISIBLE
                    binding.btnSignupSuccess.visibility = View.GONE
                    when (it) {
                        5 -> {
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
                        3 -> {
                            Log.e("ViewModel's currentPage", it.toString())
                            Log.e("ViewModel's inputHome", inputHome.value.toString())

                            // currentPage가 3일 때만 inputHome LiveData를 observe
                            val mediatorLiveData = MediatorLiveData<String>()
                            mediatorLiveData.addSource(viewModel.inputHome) {
                                // inputHome LiveData의 값이 변경될 때마다 실행되는 코드
                                if (inputHome.value == "") disableButton()
                                else ableButton()
                            }
                            mediatorLiveData.observe(this@SignupActivity) { /* NO-OP */ }
                        }
                        4 -> {
                            Log.e("ViewModel's currentPage", it.toString())
                            binding.topText = getString(R.string.signup)
                            checkButtonAvailable(4)

                        }
                    }
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
                    if(inputName.length in 1..10) {
                        ableButton()
                        return
                    }
                }

                1 -> {
                    // TODO: 프로필 페이지 버튼 유효성 검사 실시
                    ableButton()
                    return
                }

                2 -> {
                    if(inputHome.value?.length!! > 0) {
                        ableButton()
                        return
                    }
                }
                3 -> {
                    // TODO: 관심종목 페이지 버튼 유효성 검사 실시
                }
            }
            disableButton()
        }
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0 || viewPager.currentItem == 4) {
            super.onBackPressed()
            // 첫번째 (닉네임 입력 화면) 이거나 마지막 (회원가입 완료 화면) 일 경우 activity 종료
            finish()
        } else {
            // 다른 경우 viewPager 이전 페이지로 이동
            viewPager.currentItem = viewPager.currentItem - 1
            viewModel.prevPage()
        }
    }

    private inner class SignupViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        // 1. ViewPager2에 연결할 Fragment 들을 생성
        val fragmentList = listOf<Fragment>(
            SignupNameFragment(),
            SignupProfileFragment(),
            SignupAddressFragment(),
            SignupCategoryFragment(),
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

}