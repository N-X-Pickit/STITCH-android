package com.seunggyu.stitch.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.seunggyu.stitch.databinding.ItemGpsResultBinding

class GpsResultRecyclerViewAdapter(private val recyclerView: RecyclerView) :
    ListAdapter<String, RecyclerView.ViewHolder>(GpsResultDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GpsResultViewHolder(
            ItemGpsResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GpsResultViewHolder -> {
                holder.bind(getItem(position))
                Log.e("asdasd",getItem(position).toString())
            }
            else -> Log.e("error", "error")
        }
    }

    inner class GpsResultViewHolder(private val binding: ItemGpsResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String) {
            with(binding) {
                tvAddressResultItem.text = data
                Log.e("data", data)
            }
        }
    }

    class GpsResultDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}