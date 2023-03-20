package com.seunggyu.stitch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seunggyu.stitch.R
import com.seunggyu.stitch.data.model.response.HomeDataResponse
import com.seunggyu.stitch.data.model.response.NewMatch
import com.seunggyu.stitch.databinding.ItemMatchBinding
import com.seunggyu.stitch.databinding.ItemRecommendMatchBinding
import com.seunggyu.stitch.diff.HomeDiffCallback
import org.threeten.bp.LocalDate

class NewMatchRecyclerViewAdapter(private val recyclerView: RecyclerView) :
    ListAdapter<NewMatch, RecyclerView.ViewHolder>(HomeDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (recyclerView.id) {
            R.id.rv_main_contents_newmatch ->
                MatchViewHolder(
                    ItemMatchBinding.inflate(
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
            is MatchViewHolder -> holder.bind(getItem(position))
            else -> throw IllegalArgumentException("확인되지 않은 ViewHolder")
        }
    }


    inner class MatchViewHolder(private val binding: ItemMatchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // ViewHolderOne의 내용을 바인딩하는 함수
        fun bind(data: NewMatch) {
            with(binding) {
                val imageUrl = data.imageUrl.toString()

                if(data.imageUrl != "") {
                    Glide.with(root).load(imageUrl).into(ivMatchImage)
                } else {
                    Glide.with(root).load(R.drawable.background_match_null_small).into(ivMatchImage)
                }

                val startDay = data.startTime.toString().split(" ")[0]
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

                val time = data.startTime.toString().split(" ")[1].split(":")
                val hour = time[0].toInt()
                val minute = time[1].toInt()
                val ampm = "오전"
                if(hour>=12) {
                    "오후"
                    if(hour != 12) {
                        hour.minus(12)
                    }
                } else "오전"

                // 매치 종목 설정
                when (data.eventType) {
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

                // 매치 제목 설정
                tvMatchTitle.text = data.name.toString()

                // 매치 장소 설정
                tvMatchLocation.text = data.location.toString().split(" ")[1]

                // 매치 시작일
                tvMatchDate.text = "${startDaySplit[1]}.${startDaySplit[2]}($day)"

                // 매치 시간
                tvMatchTime.text = "$ampm ${"%02d".format(hour)}:${"%02d".format(minute)}"

                val maxCapacity = data.maxCapacity.toString()
                val currentMember = data.numOfMembers.toString()
                // 최대 인원
                tvParticipant.text = "$currentMember/${maxCapacity}명"

            }
        }
    }
}