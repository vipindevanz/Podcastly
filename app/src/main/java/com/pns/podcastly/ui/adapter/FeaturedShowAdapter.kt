package com.pns.podcastly.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pns.podcastly.R
import com.pns.podcastly.remote.model.Podcast
import com.pns.podcastly.ui.viewholder.FeaturedShowViewHolder

class FeaturedShowAdapter(private val showsList: MutableList<Podcast>) :
    RecyclerView.Adapter<FeaturedShowViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturedShowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shows_layout, parent, false)
        return FeaturedShowViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeaturedShowViewHolder, position: Int) {
        val model=showsList[position]
        holder.setFeaturedShowData(model)
    }

    override fun getItemCount(): Int {
        return showsList.size
    }
}