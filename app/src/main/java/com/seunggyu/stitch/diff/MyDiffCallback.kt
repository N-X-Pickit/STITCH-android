package com.seunggyu.stitch.diff

import androidx.recyclerview.widget.DiffUtil
import com.seunggyu.stitch.data.model.response.NetworkResponse

class MyDiffCallback : DiffUtil.ItemCallback<NetworkResponse>() {
    override fun areItemsTheSame(oldItem: NetworkResponse, newItem: NetworkResponse): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: NetworkResponse, newItem: NetworkResponse): Boolean {
        return oldItem == newItem
    }

}