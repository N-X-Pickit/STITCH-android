package com.seunggyu.stitch.diff

import androidx.recyclerview.widget.DiffUtil
import com.seunggyu.stitch.data.model.response.HomeDataResponse
import com.seunggyu.stitch.data.model.response.NetworkResponse
import com.seunggyu.stitch.data.model.response.NewMatch
import com.seunggyu.stitch.data.model.response.RecommendedMatch

class HomeDiffCallback : DiffUtil.ItemCallback<NewMatch>() {
    override fun areItemsTheSame(oldItem: NewMatch, newItem: NewMatch): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: NewMatch, newItem: NewMatch): Boolean {
        return oldItem == newItem
    }

}