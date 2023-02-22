package com.seunggyu.stitch.adapter

import android.content.ClipData.Item
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.seunggyu.stitch.databinding.ItemSignupProfileBinding

    class SignupProfileListAdapter(
        private var mydataSet: List<String>,
    ) :
        RecyclerView.Adapter<SignupProfileListAdapter.SignupProfileListViewHolder>() {

        class SignupProfileListViewHolder(val binding: ItemSignupProfileBinding) :
            RecyclerView.ViewHolder(binding.root) {

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SignupProfileListViewHolder {
            val binding = ItemSignupProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return SignupProfileListViewHolder(binding)
        }

        override fun onBindViewHolder(todoViewHolder: SignupProfileListViewHolder, position: Int) {
            val listposition = mydataSet[position]
            todoViewHolder.binding.tvProfileItem.text = listposition

            Log.e("RV", listposition)
            // 클릭 리스너
//            SignupProfileListViewHolder.binding.deleteImage.setOnClickListener {
//                onClickDeleteIcon.invoke(listposition) //눌렀을때 listposition를 전달하면서 함수를 실행한다.
//            }
        }

        override fun getItemCount() = mydataSet.size


    }