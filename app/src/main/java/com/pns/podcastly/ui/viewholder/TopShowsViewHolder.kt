package com.pns.podcastly.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pns.podcastly.remote.model.Podcast
import kotlinx.android.synthetic.main.shows_layout.view.*

class TopShowsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    fun setTopShowsData(model: Podcast) {
        view.apply {
            Glide.with(thumbnail).load(model.thumbnail).into(thumbnail)
            showsName.text = model.title
            publisher.text = model.publisher
        }
    }
}