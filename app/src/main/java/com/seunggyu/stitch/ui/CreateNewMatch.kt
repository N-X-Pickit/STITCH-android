package com.seunggyu.stitch.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.seunggyu.stitch.BasicActivity
import com.seunggyu.stitch.R
import com.seunggyu.stitch.databinding.ActivityCreateNewMatchBinding
import com.seunggyu.stitch.dialog.CustomAlertDialog
import com.seunggyu.stitch.ui.fragment.newmatch.MatchDetailFragment
import com.seunggyu.stitch.ui.fragment.newmatch.MatchSportFragment
import com.seunggyu.stitch.ui.fragment.newmatch.MatchTypeFragment
import com.seunggyu.stitch.ui.fragment.signup.SignupAddressFragment
import com.seunggyu.stitch.ui.fragment.signup.SignupInterestFragment
import com.seunggyu.stitch.ui.fragment.signup.SignupSuccessFragment
import com.seunggyu.stitch.viewModel.CreateNewMatchViewModel

class CreateNewMatch : BasicActivity() {
    private lateinit var binding: ActivityCreateNewMatchBinding
    private lateinit var viewPager: ViewPager2
    private val viewModel: CreateNewMatchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_new_match)

        init()
        initObserver()
        initClickListener()

    }

    private fun init() {
        viewModel.setCurrentPage(1)
        with(binding) {
            viewPager = vpNewMatch
            val pagerAdapter = CreateNewMatchViewPagerAdapter(this@CreateNewMatch)
            viewPager.adapter = pagerAdapter
            // 뷰페이저 스와이프 막기 -> 다음, 이전 버튼으로만 이동 가능
            viewPager.isUserInputEnabled = false
        }
    }

    private fun initObserver() {
        with(viewModel) {
            currentPage.observe(this@CreateNewMatch) {
                when (it) {
                    2 -> {
                        binding.topText = getString(R.string.newmatch_toptext_2)
                        binding.btnNewmatchComplete.isVisible = false
                        viewPager.currentItem++
                    }
                    else -> {
                        if (it == 3) {
                            binding.btnNewmatchComplete.isVisible = true
                            viewPager.currentItem++
                        }
                        binding.topText = getString(R.string.newmatch_toptext_1)
                    }
                }

                Log.e("current", viewPager.currentItem.toString())
                Log.e("current", viewModel.currentPage.value.toString())
            }

            isAllWriten.observe(this@CreateNewMatch) {
                with(binding) {
                    if (it) {
                        btnNewmatchComplete.isEnabled = true
                        btnNewmatchComplete.setTextColor(getColor(R.color.primary))
                    } else {
                        btnNewmatchComplete.isEnabled = false
                        btnNewmatchComplete.setTextColor(getColor(R.color.gray_10))
                    }
                }
            }
        }
    }

    private fun initClickListener() {
        with(binding) {
            btnNewmatchBack.setOnClickListener {
                showCloseDialog()
            }

            btnNewmatchComplete.setOnClickListener {
                /** TODO: 완료 버튼 서버 통신 구현 **/
            }
        }
    }

    fun showCloseDialog() {
        val title = getString(R.string.alert_newmatch_clear_title)
        val description = getString(R.string.alert_newmatch_clear_description)
        val negativeText = getString(R.string.alert_quit)
        val positiveText = getString(R.string.alert_continue)
        val dialog = CustomAlertDialog(
            this@CreateNewMatch,
            title,
            description,
            negativeText,
            positiveText
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

    private inner class CreateNewMatchViewPagerAdapter(fa: FragmentActivity) :
        FragmentStateAdapter(fa) {
        // 1. ViewPager2에 연결할 Fragment 들을 생성
        val fragmentList = listOf<Fragment>(
            MatchTypeFragment(),
            MatchSportFragment(),
            MatchDetailFragment()
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

    override fun onBackPressed() {
        showCloseDialog()
    }
}