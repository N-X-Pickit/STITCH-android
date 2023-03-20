package com.seunggyu.stitch.ui

import MatchDetailPageViewModel
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.seunggyu.stitch.R
import com.seunggyu.stitch.data.model.response.Match
import com.seunggyu.stitch.databinding.ActivityMatchDetailPageBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.max


class MatchDetailPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMatchDetailPageBinding
    private val viewModel: MatchDetailPageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_match_detail_page)


        init()
        initObserver()
    }


    private fun init() {
        viewModel.getMatchDataFromServer()
    }


    private fun initObserver() {
        with(viewModel) {
            matchData.observe(this@MatchDetailPageActivity) {
                initUi(it)
            }
        }
    }

    private fun initUi(match: Match?) {
        with(binding) {
            // 이미지 세팅
            Glide.with(this@MatchDetailPageActivity)
                .load(match?.imageUrl)
                .into(ivMatchDetailPageImage)

            // 프로필(운동종목사진)이미지 세팅, 칩 세팅
            match?.eventType?.let { setTopProfileImageAndChips(it) }

            // 제목
            tvMatchDetailPageTitle.text = match?.name

            // 주소
            tvMatchDetailPageLocation.text = match?.location

            // 시간
            tvMatchDetailPageCalendar.text = match?.startTime?.let { t -> match.duration?.let { d ->
                getMatchDateFormat(t, d)
            } }

            // 참가비
            if(match?.fee != 0) {
                tvMatchDetailPageFee.text = String.format("%,d", match?.fee) + "원"
            } else {
                if(match?.teach == true) tvMatchDetailPageFee.text = "0원"
                else tvMatchDetailPageFee.isVisible = false
            }

            // 참가인원
            val currentParty = match?.numOfMembers
            val maxParty = match?.maxCapacity
            tvMatchDetailPageParticipant.text = getString(R.string.match_detail_page_detail_participant, currentParty.toString(), maxParty.toString())

//            Glide.with(this@MatchDetailPageActivity).load(match?.).into(ivRecommendItemBackground)
        }
    }

    override fun onBackPressed() {
        finish()
    }

    fun setTopProfileImageAndChips(eventType: String) {
        when(eventType) {
            "배드민턴" -> {
                binding.cvMatchDetailPageProfile.setImageResource(R.drawable.category_badminton)
                binding.chipBadminton.root.isVisible = true}
            "야구" -> { binding.cvMatchDetailPageProfile.setImageResource(R.drawable.category_baseball)
            binding.chipBaseball.root.isVisible = true}
            "농구" -> { binding.cvMatchDetailPageProfile.setImageResource(R.drawable.category_basketball)
            binding.chipBasketball.root.isVisible = true}
            "기타" -> { binding.cvMatchDetailPageProfile.setImageResource(R.drawable.category_etc)
                binding.chipEtc.root.isVisible = true}
            "골프" -> { binding.cvMatchDetailPageProfile.setImageResource(R.drawable.category_golf)
                binding.chipGolf.root.isVisible = true}
            "헬스" -> { binding.cvMatchDetailPageProfile.setImageResource(R.drawable.category_health)
                binding.chipHealth.root.isVisible = true}
            "등산" -> { binding.cvMatchDetailPageProfile.setImageResource(R.drawable.category_mountain)
                binding.chipMountain.root.isVisible = true}
            "탁구" -> { binding.cvMatchDetailPageProfile.setImageResource(R.drawable.category_pingpong)
                binding.chipPingpong.root.isVisible = true}
            "런닝" -> { binding.cvMatchDetailPageProfile.setImageResource(R.drawable.category_running)
                binding.chipRunning.root.isVisible = true}
            "축구" -> { binding.cvMatchDetailPageProfile.setImageResource(R.drawable.category_soccer)
                binding.chipSoccer.root.isVisible = true}
            "테니스" -> { binding.cvMatchDetailPageProfile.setImageResource(R.drawable.category_tennis)
                binding.chipTennis.root.isVisible = true}

        }
    }

    fun getMatchDateFormat(date: String, duration: Int): String {
        val sb = StringBuilder()
        val splitDate = date.split(" ")
        sb.append(splitDate[0].replace('-', '.')).append(splitDate[1]).append('~')

        // "HH:mm" 형식의 문자열을 Date 객체로 파싱
        val inputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val inputTime = inputFormat.parse(splitDate[1])

        // Calendar 객체를 생성하고 파싱된 시간을 적용
        val calendar = Calendar.getInstance()
        if (inputTime != null) {
            calendar.time = inputTime
        }

        // 2시간을 더한 후, "HH:mm" 형식의 문자열로 변환
        calendar.add(Calendar.MINUTE, duration)

        sb.append(SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time))

        return sb.toString()
    }
}