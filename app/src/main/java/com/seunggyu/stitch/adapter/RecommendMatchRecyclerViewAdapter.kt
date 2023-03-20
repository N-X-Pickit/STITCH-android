package com.seunggyu.stitch.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seunggyu.stitch.R
import com.seunggyu.stitch.data.model.response.Match
import com.seunggyu.stitch.data.model.response.RecommendedMatch
import com.seunggyu.stitch.databinding.ItemRecommendMatchBinding
import com.seunggyu.stitch.diff.MyDiffCallback
import org.threeten.bp.LocalDate

class RecommendMatchRecyclerViewAdapter(private val recyclerView: RecyclerView,
                                        private val clickListener: (Match) -> Unit) :
    ListAdapter<RecommendedMatch, RecyclerView.ViewHolder>(MyDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (recyclerView.id) {
            R.id.rv_main_contents_recommend ->
                RecommendViewHolder(
                    ItemRecommendMatchBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            else -> throw IllegalArgumentException("확인되지 않은 RecyclerView ID")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RecommendViewHolder -> {
                holder.bind(getItem(position))
                holder.itemView.setOnClickListener {
                    getItem(position).match?.let { it1 -> clickListener(it1) }
                }
            }
            else -> throw IllegalArgumentException("확인되지 않은 ViewHolder")
        }
    }

    inner class RecommendViewHolder(private val binding: ItemRecommendMatchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // ViewHolderOne의 내용을 바인딩하는 함수
        fun bind(data: RecommendedMatch) {
            with(binding) {
                // 매치 배경 이미지 설정
                if(data.match?.imageUrl != "") {
                    Glide.with(root).load(data.match?.imageUrl).into(ivRecommendItemBackground)
                } else {
                    Glide.with(root).load(R.drawable.background_match_null).into(ivRecommendItemBackground)
                }
//                 개설자 프로필 이미지 설정
                /** 이곳에 개설자 프로필 이미지 추가할 것 **/
                val hostImageUrl = data.hostMember?.imageUrl
                if(hostImageUrl != ""){
                    Glide.with(root).load(hostImageUrl)
                        .into(cvRecommendItemProfileImage)
                }else {
                    Glide.with(root).load(R.drawable.default_profile_image)
                        .into(cvRecommendItemProfileImage)
                }
                // 개설 닉네임 설정
                tvRecommendItemNickname.text = data.hostMember?.name

                // 매치 종목 설정
                when (data.match?.eventType) {
                    "테니스" -> chipTennis.root.isVisible = true
                    "배드민턴" -> chipBadminton.root.isVisible = true
                    "탁구" -> chipPingpong.root.isVisible = true
                    "축구" -> chipSoccer.root.isVisible = true
                    "런닝" -> chipRunning.root.isVisible = true
                    "등산" -> chipMountain.root.isVisible = true
                    "헬스" -> chipHealth.root.isVisible = true
                    "농구" -> chipBasketball.root.isVisible = true
                    "야구" -> chipBaseball.root.isVisible = true
                    "골프" -> chipGolf.root.isVisible = true
                    "기타" -> chipEtc.root.isVisible = true
                }
                val startDay = data.match?.startTime.toString().split(" ")[0]
                Log.e("startDay ", data.match?.startTime.toString())
                val startDaySplit = startDay.split("-")
                val date = LocalDate.of(startDaySplit[0].toInt(), startDaySplit[1].toInt(), startDaySplit[2].toInt())

                val dayOfWeek = date.dayOfWeek.value
                var day = "일"
                when(dayOfWeek) {
                    1 -> day = "월"
                    2 -> day = "화"
                    3 -> day = "수"
                    4 -> day = "목"
                    5 -> day = "금"
                    6 -> day = "토"
                    7 -> day = "일"
                }

                val time = data.match?.startTime.toString().split(" ")[1].split(":")
                val hour = time[0].toInt()
                val minute = time[1].toInt()
                val ampm = "오전"
                if(hour>=12) {
                    "오후"
                    if(hour != 12) {
                        hour.minus(12)
                    }
                } else "오전"

                // 매치 제목 설정
                tvRecommendItemTitle.text = data.match?.name.toString()

                // 매치 장소 설정
                tvRecommendItemLocation.text = data.match?.location.toString().split(" ")[1]

                // 매치 시작일
                tvRecommendItemDate.text = "${startDaySplit[1]}.${startDaySplit[2]}($day)"

                // 매치 시간
                tvRecommendItemTime.text = "$ampm ${"%02d".format(hour)}:${"%02d".format(minute)}"

                val maxCapacity = data.match?.maxCapacity.toString()
                val currentMember = data.match?.numOfMembers.toString()
                // 최대 인원
                tvRecommendParticipant.text = "$currentMember/${maxCapacity}명"
            }
        }
    }
}