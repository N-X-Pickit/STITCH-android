package com.seunggyu.stitch.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seunggyu.stitch.R
import com.seunggyu.stitch.data.model.response.EventResponse

class BannerPagerAdapter : RecyclerView.Adapter<BannerPagerAdapter.BannerViewHolder> {
    private var eventList: List<EventResponse>
    var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_banner, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        // infinite View Pager를 위한 position 변수
        val actualPosition = holder.bindingAdapterPosition % eventList.size

        // 각 position에 해당하는 url을 리스트로부터 받아온다.
        val imageUrl: String = eventList[actualPosition].imageUrl.toString()
        val title: String = eventList[actualPosition].title.toString()
        Glide.with(holder.itemView.context).load(imageUrl).into(holder.ivBanner)
        holder.tvBanner.text = title
        // 클릭 리스너 ..
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    constructor(arrayList: List<EventResponse>) {
        eventList = arrayList
    }

    constructor(arrayList: List<EventResponse>, context: Context?) {
        eventList = arrayList
        this.context = context
    }

    inner class BannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ivBanner: ImageView
        var tvBanner: TextView

        init {
            ivBanner = view.findViewById<ImageView>(R.id.iv_event)
            tvBanner = view.findViewById<TextView>(R.id.tv_event)
        }
    }
}