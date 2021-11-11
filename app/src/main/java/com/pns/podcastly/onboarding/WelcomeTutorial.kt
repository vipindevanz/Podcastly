package com.pns.podcastly.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.pns.podcastly.MainActivity
import com.pns.podcastly.R
import kotlinx.android.synthetic.main.activity_welcome_tutorial.*
import me.relex.circleindicator.CircleIndicator3

class WelcomeTutorial : AppCompatActivity() {
    private var titleList = mutableListOf<String>()
    private var descList = mutableListOf<String>()
    private var imagesList = mutableListOf<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_tutorial)
        setViewPager()

        btn_next.setOnClickListener {
            val crt = slide_view_pager.currentItem
            when (crt) {
                0 -> slide_view_pager.currentItem = 1
                1 -> slide_view_pager.currentItem = 2
                2 -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }

            }
        }
        btn_skip.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }

    private fun setViewPager() {

        postToList()

        slide_view_pager.adapter = SliderPagerAdapter(titleList, descList, imagesList)
        slide_view_pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val indicator = findViewById<CircleIndicator3>(R.id.circularIndicator)
        indicator.setViewPager(slide_view_pager)
    }

    private fun addToList(title: String, desc: String, image: Int) {
        titleList.add(title)
        descList.add(desc)
        imagesList.add(image)
    }

    private fun postToList() {
        addToList(
            "Welcome to podcastly",
            "Create podcasts and go live. All-in-one audio recorder and podcast creator to make a podcast",
            R.drawable.first_sc_image
        )
        addToList(
            "Edit your recording",
            "Professional podcast recording studio app with easy-to-use interface.Split, Trim, Noise cancelling and more!",
            R.drawable.second_sc_image
        )
        addToList(
            "Endless hours of free \npodcasts",
            " Listen to an exciting variety of live audio shows.\n" +
                    "Engage with the host and other listeners with messages",
            R.drawable.third_sc_image
        )
    }
}