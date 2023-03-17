package com.seunggyu.stitch.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.seunggyu.stitch.R
import com.seunggyu.stitch.data.model.response.NetworkResponse
import com.seunggyu.stitch.databinding.ItemMatchBinding
import com.seunggyu.stitch.databinding.ItemRecommendMatchBinding
import com.seunggyu.stitch.diff.MyDiffCallback

class MyRecyclerViewAdapter(private val recyclerView: RecyclerView) :
    ListAdapter<NetworkResponse, RecyclerView.ViewHolder>(MyDiffCallback()) {
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
            is RecommendViewHolder -> holder.bind(getItem(position))
            is MatchViewHolder -> holder.bind(getItem(position))
            else -> throw IllegalArgumentException("확인되지 않은 ViewHolder")
        }
    }


    inner class MatchViewHolder(private val binding: ItemMatchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // ViewHolderOne의 내용을 바인딩하는 함수
        fun bind(data: NetworkResponse) {
            with(binding) {
                // 매치 이미지 설정
                Glide.with(root).load(data.imageUrl.toString()).into(ivMatchImage)

                // 매치 종목 설정
                when (data.sports?.get(0)) {
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
                tvMatchTitle.text = data.contents.toString()

                // 매치 장소 설정
                tvMatchLocation.text = data.location.toString()

                // 매치 시작일
                tvMatchDate.text = data.hostId.toString()

                // 매치 시간
                tvMatchTime.text = data.startTime.toString()

                // 최대 인원
                tvParticipant.text = root.context.getString(
                    R.string.participant,
                    data.duration.toString(),
                    data.fee.toString()
                )
            }
        }
    }

    inner class RecommendViewHolder(private val binding: ItemRecommendMatchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // ViewHolderOne의 내용을 바인딩하는 함수
        fun bind(data: NetworkResponse) {
            with(binding) {
                // 매치 배경 이미지 설정
                Glide.with(root).load(data.imageUrl.toString()).into(ivRecommendItemBackground)

                // 개설자 프로필 이미지 설정
                Glide.with(root).load(data.email.toString())
                    .into(object : CustomTarget<Drawable>() {
                        override fun onResourceReady(
                            resource: Drawable,
                            transition: Transition<in Drawable>?
                        ) {
                            cvRecommendItemProfileImage.background = resource
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                            cvRecommendItemProfileImage.background =
                                ContextCompat.getDrawable(root.context, R.drawable.ic_person)
                        }
                    })

                // 개설 닉네임 설정
                tvRecommendItemNickname.text = data.name.toString()

                // 매치 종목 설정
                when (data.sports?.get(0)) {
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
                tvRecommendItemTitle.text = data.contents.toString()

                // 매치 장소 설정
                tvRecommendItemLocation.text = data.location.toString()

                // 매치 시작일
                tvRecommendItemDate.text = data.hostId.toString()

                // 매치 시간
                tvRecommendItemTime.text = data.startTime.toString()

                // 최대 인원
                tvRecommendParticipant.text = root.context.getString(
                    R.string.participant,
                    data.duration.toString(),
                    data.fee.toString()
                )
            }
        }
    }
}