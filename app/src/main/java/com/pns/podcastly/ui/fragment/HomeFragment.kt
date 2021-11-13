package com.pns.podcastly.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.pns.podcastly.ui.adapter.BannerAdapter
import com.pns.podcastly.database.model.BannerModel
import com.pns.podcastly.R
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(R.layout.fragment_home) {
    private val bannerImages = mutableListOf<BannerModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buildBannerSliderList()
        setBannerSlider()
    }


    private fun buildBannerSliderList() {
        bannerImages.add(BannerModel(R.drawable.banner1))
        bannerImages.add(BannerModel(R.drawable.banner2))
        bannerImages.add(BannerModel(R.drawable.banner3))
        bannerImages.add(BannerModel(R.drawable.banner4))
    }

    private fun setBannerSlider() {
        val bannerAdapter = BannerAdapter(bannerImages)
        bannerSlider.setSliderAdapter(bannerAdapter)
        bannerSlider.setIndicatorAnimation(IndicatorAnimationType.FILL)
        bannerSlider.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)
        bannerSlider.startAutoCycle()
    }
}