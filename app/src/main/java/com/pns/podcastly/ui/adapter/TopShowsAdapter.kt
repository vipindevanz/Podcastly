package com.pns.podcastly.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pns.podcastly.R
import com.pns.podcastly.remote.model.Podcast
import com.pns.podcastly.ui.viewholder.TopShowsViewHolder

class TopShowsAdapter(private val showsList: MutableList<Podcast>) :
    RecyclerView.Adapter<TopShowsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopShowsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shows_layout, parent, false)
        return TopShowsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopShowsViewHolder, position: Int) {
        val model=showsList[position]
        holder.setTopShowsData(model)
    }

    override fun getItemCount(): Int {
        return 9
    }
}