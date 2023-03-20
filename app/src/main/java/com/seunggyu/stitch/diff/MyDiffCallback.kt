package com.seunggyu.stitch.diff

import androidx.recyclerview.widget.DiffUtil
import com.seunggyu.stitch.data.model.response.NetworkResponse
import com.seunggyu.stitch.data.model.response.NewMatch
import com.seunggyu.stitch.data.model.response.RecommendedMatch

class MyDiffCallback : DiffUtil.ItemCallback<RecommendedMatch>() {
    override fun areItemsTheSame(oldItem: RecommendedMatch, newItem: RecommendedMatch): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: RecommendedMatch, newItem: RecommendedMatch): Boolean {
        return oldItem == newItem
    }

}