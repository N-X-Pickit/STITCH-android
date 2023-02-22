package com.seunggyu.stitch.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.seunggyu.stitch.adapter.SignupProfileListAdapter
import com.seunggyu.stitch.databinding.ActivitySignupBinding
import com.seunggyu.stitch.ui.fragment.*
import com.seunggyu.stitch.viewModel.SignupViewModel
import kotlinx.coroutines.*

private const val NUM_PAGES = 3
class SignupActivity : FragmentActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var viewPager: ViewPager2

    private var signupPageIndicator = 0  // 현재 보여지고 있는 페이지

    private val viewModel: SignupViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        uiInit()
        init()

        val pagerAdapter = SignupViewPagerAdapter(this)
        viewPager.adapter = pagerAdapter
        // 뷰페이저 스와이프 막기 -> 다음, 이전 버튼으로만 이동 가능
//        viewPager.isUserInputEnabled = false
    }

    private fun uiInit() {
        signupPageIndicator = 1
    }

    private fun init() {

        with(binding) {
            viewPager = vpSignup
            // 다음버튼 클릭시 progressbar 25씩 증가
            btnSignupNext.setOnClickListener {
                if (progressSignup.progress <= 100) {
                    CoroutineScope(Dispatchers.Main).launch {
                        repeat(20) {
                            progressSignup.progress += 1

                            delay(10)

                        }
                    }
                }
            }

            // 뒤로가기 버튼 클릭시 progressbar 25씩 감소
            btnSignupBack.setOnClickListener {
                if (progressSignup.progress <= 20) {
                    finish()
                }
                CoroutineScope(Dispatchers.Main).launch {
                    repeat(20) {
                        progressSignup.progress -= 1

                        delay(10)

                    }
                }
            }
//            btnSignupNext.background = getDrawable()
        }
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    private inner class SignupViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        // 1. ViewPager2에 연결할 Fragment 들을 생성
        val fragmentList = listOf<Fragment>(SignupNameFragment(), SignupProfileFragment(), SignupAddressFragment(), SignupCategoryFragment(), SignupSuccessFragment())

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