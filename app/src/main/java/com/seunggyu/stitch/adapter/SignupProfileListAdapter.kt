package com.seunggyu.stitch.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.seunggyu.stitch.R
import com.seunggyu.stitch.databinding.ItemSignupProfileBinding
import com.seunggyu.stitch.viewModel.SignupViewModel

class SignupProfileListAdapter(
    private var mydataSet: List<String>,
    private var selectedItemIndex: Int = -1,
    private var viewModel: SignupViewModel,
) : RecyclerView.Adapter<SignupProfileListAdapter.SignupProfileListViewHolder>() {

    class SignupProfileListViewHolder(
        private val binding: ItemSignupProfileBinding,
        private val adapter: SignupProfileListAdapter
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String, selected: Boolean, viewModel: SignupViewModel) {
            binding.tvProfileItem.text = item
            binding.itemSignupProfile.strokeColor =
                if (selected) ContextCompat.getColor(itemView.context, R.color.yellow_06)
                else ContextCompat.getColor(itemView.context, R.color.black_transparent_02)
            itemView.setOnClickListener {
                val previousSelectedIndex = adapter.selectedItemIndex
                adapter.selectedItemIndex = adapterPosition

                // 이전에 선택했던 item이 있다면 strokeColor 변경
                if (previousSelectedIndex != -1) {
                    adapter.notifyItemChanged(previousSelectedIndex)
                }
                // 현재 선택한 item의 strokeColor 변경
                adapter.notifyItemChanged(adapterPosition)

                viewModel.setProfileSelectedItem(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SignupProfileListViewHolder {
        val binding = ItemSignupProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SignupProfileListViewHolder(binding, this)
    }

    override fun onBindViewHolder(holder: SignupProfileListViewHolder, position: Int) {
        val listposition = mydataSet[position]
        holder.bind(listposition, position == selectedItemIndex, viewModel)
    }

    override fun getItemCount() = mydataSet.size

}
