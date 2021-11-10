package com.pns.podcastly.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pns.podcastly.R


class SliderPagerAdapter(
    private var title: List<String>,
    private var desc: List<String>,
    private var images: List<Int>
) : RecyclerView.Adapter<SliderPagerAdapter.Pager2ViewHolder>() {
    inner class Pager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.tv_title_onboard)
        val itemDesc: TextView = itemView.findViewById(R.id.tv_desc_onboard)
        val itemImage: ImageView = itemView.findViewById(R.id.iv_pic_onboard)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SliderPagerAdapter.Pager2ViewHolder {
        return Pager2ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_item_screen, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SliderPagerAdapter.Pager2ViewHolder, position: Int) {
        holder.itemTitle.text = title[position]
        holder.itemDesc.text = desc[position]
        holder.itemImage.setImageResource(images[position])
    }

    override fun getItemCount(): Int {
        return title.size
    }


}