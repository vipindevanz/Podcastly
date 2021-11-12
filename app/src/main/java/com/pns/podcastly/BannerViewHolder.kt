package com.pns.podcastly

import android.view.View
import com.smarteist.autoimageslider.SliderViewAdapter
import kotlinx.android.synthetic.main.banner_layout.view.*


class BannerViewHolder(private val view: View?) : SliderViewAdapter.ViewHolder(view) {

    fun setBannerData(model: BannerModel) {
        view?.apply {
            banner_img.setImageResource(model.bannerImage)
        }
    }

}