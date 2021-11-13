package com.pns.podcastly.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.pns.podcastly.R
import com.pns.podcastly.database.model.BannerModel
import com.pns.podcastly.remote.model.Podcast
import com.pns.podcastly.ui.adapter.BannerAdapter
import com.pns.podcastly.ui.adapter.FeaturedShowAdapter
import com.pns.podcastly.ui.adapter.TopShowsAdapter
import com.pns.podcastly.viewmodel.PodcastViewModel
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*


class HomeFragment : Fragment(R.layout.fragment_home) {
    private val bannerImages = mutableListOf<BannerModel>()
    private val podcastList = mutableListOf<Podcast>()
    lateinit var topShowsAdapter: TopShowsAdapter
    lateinit var featuredShowAdapter: FeaturedShowAdapter
    lateinit var viewModel: PodcastViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buildBannerSliderList()
        setBannerSlider()
        setRecyclerView()
        viewModel = ViewModelProviders.of(this).get(PodcastViewModel::class.java)
        podcastList.addAll(viewModel.showData() as ArrayList<Podcast>)
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

    private fun setRecyclerView() {
        topShows()
        featuredShow()
        liveShow()
    }

    private fun topShows() {
        val adapter = TopShowsAdapter(podcastList)
        topShowsRecyclerview.layoutManager=GridLayoutManager(context,3)
        topShowsRecyclerview.adapter=adapter
    }

    private fun featuredShow() {
        val adapter = TopShowsAdapter(podcastList)
    }

    private fun liveShow() {
        val adapter = TopShowsAdapter(podcastList)
    }
}