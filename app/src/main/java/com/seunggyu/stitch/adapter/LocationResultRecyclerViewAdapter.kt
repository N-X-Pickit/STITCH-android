package com.seunggyu.stitch.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.seunggyu.stitch.data.model.Location
import com.seunggyu.stitch.databinding.ItemGpsResultBinding

class LocationResultRecyclerViewAdapter(private val recyclerView: RecyclerView,
                                        private val clickListener: (Location) -> Unit) :
    ListAdapter<Location, RecyclerView.ViewHolder>(GpsResultDiffCallback()) {
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
                holder.itemView.setOnClickListener {
                    clickListener(getItem(position))
                }
            }
            else -> Log.e("error", "error")
        }
    }

    inner class GpsResultViewHolder(private val binding: ItemGpsResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Location) {
            with(binding) {
                tvAddressResultItem.text = data.title
                Log.e("data", data.title)
                Log.e("katech X", data.katechX)
                Log.e("katech Y", data.katechY)
            }
        }
    }

    class GpsResultDiffCallback : DiffUtil.ItemCallback<Location>() {
        override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem == newItem
        }
    }
}