package com.seunggyu.stitch.ui.fragment.home

import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayoutMediator
import com.seunggyu.stitch.GlobalApplication
import com.seunggyu.stitch.R
import com.seunggyu.stitch.Util.SnackBarCustom
import com.seunggyu.stitch.adapter.BannerPagerAdapter
import com.seunggyu.stitch.databinding.FragMainHomeBinding
import java.util.*

class HomeFragment : Fragment() {
    private lateinit var binding: FragMainHomeBinding

    private lateinit var adapter: BannerPagerAdapter
    private var currentPage = Int.MAX_VALUE / 2
    private var timer: Timer? = null
    private val DELAY_MS: Long = 5000 // (초기 웨이팅 타임) ex) 앱 로딩 후 5초 뒤 플립됨.
    private val PERIOD_MS: Long = 5000 // 5초 주기로 배너 이동

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_main_home, container, false)
        initClickListener()
        initObserver()
        initData()
        initBannerViewPager()

        return binding.root
    }

    private fun initBannerViewPager() {

    }

    private fun initObserver() {

    }

    private fun initClickListener() {

    }

    private fun initData() {
        // 배너 관련 데이터
        GlobalApplication.bannerData.let {
            if (it != null) {
                for(index in it.indices) {
                    Log.e("imageurl", it[index].imageUrl.toString())
                    Log.e("title", it[index].title.toString())
                }
                adapter = BannerPagerAdapter(it, requireContext())
                binding.vpBanner.adapter = adapter

                binding.vpBanner.setCurrentItem(currentPage, false)

                val indicatorLayout = binding.indicatorLayout
                updateIndicator(0, it.size)

                binding.vpBanner.registerOnPageChangeCallback(object : OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)

                        indicatorLayout.removeAllViews()
                        // indicator 업데이트
                        updateIndicator(position % it.size, it.size)

                        // 스크롤 했을 때 현재 페이지를 설정해준다.
                        // 자동 스크롤의 경우 currentPage의 변수 값을 현재 position으로 잡는데
                        // 2페이지에서 사용자가 1페이지로 슬라이드 했을 때
                        // 자동 스크롤 시 다음 페이지를 3번이 아닌 2번으로 다시 바꿔줌
                        currentPage = position
                        if (timer != null) {
                            // 타이머 종료 후 재실
                            timer!!.cancel()
                            timer = null
                            timerStart()
                        }


                        // 페이지가 스크롤(자동스크롤, 사용자 직접 스크롤 모두 포함)될 때마다 페이지 번호를 갱신
//                    eventPageDescript = getString(
//                        R.string.EventViewPagerDescript,
//                        position % listSize + 1,
//                        listSize
//                    )
//                    binding.tvPagenum.setText(eventPageDescript)
                    }
                })
//                binding.dotsIndicator.attachTo(binding.vpBanner)

            } else {
                SnackBarCustom.make(binding.clHomeview, getString(R.string.snackbar_banner_error)).show()
            }

        }
    }

    // viewpager의 indicator를 업데이트 하는 함수
    private fun updateIndicator(currentPosition: Int, size: Int) {
        for (i in 0 until size) {
            val imageView = ImageView(context)
            val layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            if(i != size-1) layoutParams.setMargins(0, 0, 16.dpToPx(), 0) // 각 ImageView 사이의 간격을 설정(가장 우측 제외)
            imageView.layoutParams = layoutParams

            if (i == currentPosition) { // 현재 페이지에 해당하는 ImageView를 선택된 이미지로 설정합니다.
                imageView.setImageResource(R.drawable.selected_dot)
            } else { // 나머지 페이지에 해당하는 ImageView를 일반 이미지로 설정합니다.
                imageView.setImageResource(R.drawable.default_dot)
            }

            binding.indicatorLayout.addView(imageView) // ImageView를 LinearLayout에 추가합니다.
        }
    }


    private fun timerStart() {
        // Adapter 세팅 후 타이머 실행
        val handler = Handler()
        val Update = Runnable {
            val nextPage = currentPage + 1
            binding.vpBanner.setCurrentItem(nextPage, true)
            currentPage = nextPage
        }
        timer = Timer()
        // thread에 작업용 thread 추가
        timer?.schedule(object : TimerTask() {
            override fun run() {
                handler.post(Update)
            }
        }, DELAY_MS, PERIOD_MS)
    }

    override fun onResume() {
        super.onResume()
        timerStart()
    }

    override fun onPause() {
        super.onPause()
        // 다른 탭으로 이동 시 타이머 중지시킴
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
    }

    fun Int.dpToPx(): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, this.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()
    }
}