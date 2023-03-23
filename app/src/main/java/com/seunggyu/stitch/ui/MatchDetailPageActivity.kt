package com.seunggyu.stitch.ui

import MatchDetailPageViewModel
import MenuMemberBottomSheetDialog
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.seunggyu.stitch.BasicActivity
import com.seunggyu.stitch.GlobalApplication
import com.seunggyu.stitch.R
import com.seunggyu.stitch.Util.SnackBarCustom
import com.seunggyu.stitch.Util.SnackBarLabelCustom
import com.seunggyu.stitch.data.model.response.Match
import com.seunggyu.stitch.databinding.ActivityMatchDetailPageBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs
import kotlin.math.max


class MatchDetailPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMatchDetailPageBinding
    private val viewModel: MatchDetailPageViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_match_detail_page)

        initObserver()
        initInsetMargin()
        init()
        initAppBar()
        initClickListener()
    }


    private fun init() {
        viewModel.setPassedMatchId(intent.getStringExtra("id")!!)
        val toolbar = binding.toolbar
        (this@MatchDetailPageActivity).setSupportActionBar(toolbar)

        getIntentDataFromHome()
        viewModel.getMatchDataFromServer()
        viewModel.getHostInfo(intent.getStringExtra("hostId").toString())
    }

    private fun initClickListener() {
        binding.btnMatchDetailPageBack.setOnClickListener {
            finish()
        }

        binding.btnMatchDetailPageMenu.setOnClickListener {
            val bottomSheet = MenuMemberBottomSheetDialog(0) // 참여자인 경우
            supportFragmentManager.let { it1 -> bottomSheet.show(it1, bottomSheet.tag) }
        }

        binding.btnMatchDetailAbled.setOnClickListener {
            viewModel.joinMatch()
        }

        binding.btnMatchDetailPageSetting.setOnClickListener {
            val intent = Intent(this@MatchDetailPageActivity, MatchSettingActivity::class.java)
            intent.putExtra("matchId", viewModel.matchData.value?.id)
            startActivity(intent)
        }

    }

    private fun getIntentDataFromHome() {
        viewModel.setMatchData(
            Match(
                id = intent.getStringExtra("id"),
                type = intent.getStringExtra("type"),
                location = intent.getStringExtra("location"),
                imageUrl = intent.getStringExtra("imageUrl"),
                name = intent.getStringExtra("name"),
                detail = intent.getStringExtra("detail"),
                startTime = intent.getStringExtra("startTime"),
                duration = intent.getIntExtra("duration", 30),
                eventType = intent.getStringExtra("eventType"),
                hostId = intent.getStringExtra("hostId"),
                maxCapacity = intent.getIntExtra("maxCapacity", 2),
                fee = intent.getIntExtra("fee", 0),
                latitude = intent.getStringExtra("latitude"),
                longitude = intent.getStringExtra("longitude"),
                teach = intent.getBooleanExtra("teach", false),
                numOfMembers = intent.getIntExtra("numOfMembers", 1),
                sk = "0")
            )
    }

    private fun initObserver() {
        with(viewModel) {
            matchData.observe(this@MatchDetailPageActivity) {
                initUi(it)
            }

            hostName.observe(this@MatchDetailPageActivity) {
                binding.tvMatchDetailPageHostName.text = it
                binding.tvMatchDetailPageHostNameBottom.text = it
            }

            hostImageUrl.observe(this@MatchDetailPageActivity) {
                if(it != "") {
                    Glide.with(this@MatchDetailPageActivity).load(it)
                        .into(binding.cvMatchDetailPageHostProfileImage)
                    Glide.with(this@MatchDetailPageActivity).load(it)
                        .into(binding.cvMatchDetailPageHostProfileImageBottom)
                    Log.e("asdasdasdasd", "asdasdafsjkdhskajvjkadn")
                }else {
                    Glide.with(this@MatchDetailPageActivity).load(R.drawable.default_profile_image)
                        .into(binding.cvMatchDetailPageHostProfileImage)
                    Glide.with(this@MatchDetailPageActivity).load(R.drawable.default_profile_image)
                        .into(binding.cvMatchDetailPageHostProfileImageBottom)
                }
            }

            isAlreadyMember.observe(this@MatchDetailPageActivity) {
                if(it) {
                    binding.btnMatchDetailDisabled.isVisible = true
                    binding.btnMatchDetailAbled.isVisible=false
                    binding.btnMatchDetailPageSetting.isVisible = false
                    binding.btnMatchDetailPageMenu.isVisible = true
                    if(viewModel.hostId.value == GlobalApplication.prefs.getCurrentUser().id) {
                        binding.btnMatchDetailPageSetting.isVisible = true
                        binding.btnMatchDetailPageMenu.isVisible = false
                    }
                } else {
                    binding.btnMatchDetailDisabled.isVisible = false
                    binding.btnMatchDetailAbled.isVisible = true
                    binding.btnMatchDetailPageSetting.isVisible = false
                    binding.btnMatchDetailPageMenu.isVisible = true
                }
            }

            joinSuccess.observe(this@MatchDetailPageActivity) {
                if(it) {
                    SnackBarLabelCustom.make(binding.root, "성공적으로 매치에 참여했습니다")
                }
            }

            joinedMember.observe(this@MatchDetailPageActivity) {
                var tt = false
                val uid = GlobalApplication.prefs.getString("userId")
                for(i in it){
                    if(uid == i.id) {
                        tt = true
                        break
                    }
                }
                if(tt) {
                    viewModel.setIsAlreadyMember(true)
                }
            }
        }
    }

    private fun initUi(match: Match?) {
        with(binding) {
            // 티치매치 여부 확인
            if(match?.teach == true) {
                chipClass.root.isVisible = true
                ivMatchDetailPageFee.isVisible = true
                tvMatchDetailPageFee.isVisible = true
            }
            Log.e("ImageUrl=============", match?.imageUrl.toString())

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
            tvMatchDetailPageCalendar.text = match?.startTime?.let { t ->
                match.duration?.let { d ->
                    getMatchDateFormat(t, d)
                }
            }

            // 참가비
            if (match?.fee != 0) {
                tvMatchDetailPageFee.text = String.format("%,d", match?.fee) + "원"
            } else {
                if (match?.teach == true) tvMatchDetailPageFee.text = "0원"
                else tvMatchDetailPageFee.isVisible = false
            }

            // 참가인원
            val currentParty = match?.numOfMembers
            val maxParty = match?.maxCapacity
            tvMatchDetailPageParticipant.text = getString(
                R.string.match_detail_page_detail_participant,
                currentParty.toString(),
                maxParty.toString()
            )
            tvMatchDetailPageDetail.text = match?.detail
//            Glide.with(this@MatchDetailPageActivity).load(match?.ho).into()
        }
    }

    override fun onBackPressed() {
        finish()
    }

    fun setTopProfileImageAndChips(eventType: String) {
        when (eventType) {
            "배드민턴" -> {
                binding.cvMatchDetailPageProfile.setImageResource(R.drawable.category_badminton)
                binding.chipBadminton.root.isVisible = true
            }
            "야구" -> {
                binding.cvMatchDetailPageProfile.setImageResource(R.drawable.category_baseball)
                binding.chipBaseball.root.isVisible = true
            }
            "농구" -> {
                binding.cvMatchDetailPageProfile.setImageResource(R.drawable.category_basketball)
                binding.chipBasketball.root.isVisible = true
            }
            "기타" -> {
                binding.cvMatchDetailPageProfile.setImageResource(R.drawable.category_etc)
                binding.chipEtc.root.isVisible = true
            }
            "골프" -> {
                binding.cvMatchDetailPageProfile.setImageResource(R.drawable.category_golf)
                binding.chipGolf.root.isVisible = true
            }
            "헬스" -> {
                binding.cvMatchDetailPageProfile.setImageResource(R.drawable.category_health)
                binding.chipHealth.root.isVisible = true
            }
            "등산" -> {
                binding.cvMatchDetailPageProfile.setImageResource(R.drawable.category_mountain)
                binding.chipMountain.root.isVisible = true
            }
            "탁구" -> {
                binding.cvMatchDetailPageProfile.setImageResource(R.drawable.category_pingpong)
                binding.chipPingpong.root.isVisible = true
            }
            "런닝" -> {
                binding.cvMatchDetailPageProfile.setImageResource(R.drawable.category_running)
                binding.chipRunning.root.isVisible = true
            }
            "축구" -> {
                binding.cvMatchDetailPageProfile.setImageResource(R.drawable.category_soccer)
                binding.chipSoccer.root.isVisible = true
            }
            "테니스" -> {
                binding.cvMatchDetailPageProfile.setImageResource(R.drawable.category_tennis)
                binding.chipTennis.root.isVisible = true
            }

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

    private fun initInsetMargin() = with(binding) {
        ViewCompat.setOnApplyWindowInsetsListener(coordinator) { v: View, insets: WindowInsetsCompat ->
            val params = v.layoutParams as ViewGroup.MarginLayoutParams
            params.bottomMargin = insets.systemWindowInsetBottom
            toolbarContainer.layoutParams =
                (toolbarContainer.layoutParams as ViewGroup.MarginLayoutParams).apply {
                    setMargins(16.dpToPx(), insets.systemWindowInsetTop, 16.dpToPx(), 0)
                }
            collapsingToolbarContainer.layoutParams =
                (collapsingToolbarContainer.layoutParams as ViewGroup.MarginLayoutParams).apply {
                    setMargins(0, 0, 0, 0)
                }

            insets.consumeSystemWindowInsets()
        }
    }

    fun Int.dpToPx(): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, this.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()
    }

    private fun initAppBar() {

        binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val topPadding = binding.toolbarBackgroundView.height.toFloat() * 3
            val realAlphaScrollHeight = appBarLayout.measuredHeight - appBarLayout.totalScrollRange
            val abstractOffset = abs(verticalOffset)

            val realAlphaVerticalOffset =
                if (abstractOffset - topPadding < 0) 0f else abstractOffset - topPadding
            if (abstractOffset < topPadding) {
                binding.toolbarBackgroundView.alpha = 0f
                return@OnOffsetChangedListener
            }
            val percentage = realAlphaVerticalOffset / realAlphaScrollHeight
            binding.toolbarBackgroundView.alpha =
                1 - (if (1 - percentage * 2 < 0) 0f else 1 - percentage * 2)

        })
        initActionBar()
    }

    private fun initActionBar() = with(binding) {
        toolbar.navigationIcon = null
        toolbar.setContentInsetsAbsolute(0, 0)
        (this@MatchDetailPageActivity).setSupportActionBar(toolbar)
        (this@MatchDetailPageActivity).supportActionBar?.let {
            it.setHomeButtonEnabled(false)
            it.setDisplayHomeAsUpEnabled(false)
            it.setDisplayShowHomeEnabled(false)
        }
    }
}