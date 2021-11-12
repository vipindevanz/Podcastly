package com.pns.podcastly

import android.view.LayoutInflater
import android.view.ViewGroup
import com.smarteist.autoimageslider.SliderViewAdapter

class BannerAdapter(private val bannerList: MutableList<BannerModel>) :
    SliderViewAdapter<BannerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup?): BannerViewHolder {
        val view =
            LayoutInflater.from(parent?.context).inflate(R.layout.banner_layout, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: BannerViewHolder?, position: Int) {
        val model = bannerList[position]
        viewHolder?.setBannerData(model)
    }

    override fun getCount(): Int {
        return bannerList.size
    }
}