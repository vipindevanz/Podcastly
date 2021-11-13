package com.pns.podcastly.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.pns.podcastly.R
import com.pns.podcastly.database.model.BannerModel
import com.pns.podcastly.remote.model.Podcast
import com.pns.podcastly.remote.model.RXModel
import com.pns.podcastly.ui.adapter.BannerAdapter
import com.pns.podcastly.ui.adapter.FeaturedShowAdapter
import com.pns.podcastly.ui.adapter.TopShowsAdapter
import com.pns.podcastly.viewmodel.PodcastViewModel
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(R.layout.fragment_home) {
    private val bannerImages = mutableListOf<BannerModel>()
    private val podcastList = mutableListOf<Podcast>()
    private val topPodcastList = mutableListOf<Podcast>()
    lateinit var topShowsAdapter: TopShowsAdapter
    lateinit var featuredShowAdapter: FeaturedShowAdapter
    private lateinit var viewModel: PodcastViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(PodcastViewModel::class.java)
        buildBannerSliderList()
        setBannerSlider()
        buildList()
        setRecyclerView()


    }

    private fun buildBannerSliderList() {
        bannerImages.add(BannerModel(R.drawable.banner1))
        bannerImages.add(BannerModel(R.drawable.banner2))
        bannerImages.add(BannerModel(R.drawable.banner3))
        bannerImages.add(BannerModel(R.drawable.banner4))
    }

    private fun buildList() {
        viewModel.showData()
        viewModel.liveData.observe(viewLifecycleOwner, Observer {
            it?.run {
                when (it) {
                    is RXModel.OnSuccess -> {
                        podcastList.addAll(it.response.podcasts)
                        for (index in 0..9) {
                            topPodcastList.add(podcastList[index])
                        }
                        topShowsAdapter.notifyDataSetChanged()
                    }
                    is RXModel.OnFailure -> {
                        Toast.makeText(context, "Some error occur", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
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
        topShowsAdapter = TopShowsAdapter(topPodcastList)
        topShowsRecyclerview.layoutManager = GridLayoutManager(context, 3)
        topShowsRecyclerview.adapter = topShowsAdapter
    }

    private fun featuredShow() {
        featuredShowAdapter = FeaturedShowAdapter(podcastList)
        featuresRecyclerView.layoutManager=GridLayoutManager(context,3)
        featuresRecyclerView.adapter=featuredShowAdapter
    }

    private fun liveShow() {

    }
}