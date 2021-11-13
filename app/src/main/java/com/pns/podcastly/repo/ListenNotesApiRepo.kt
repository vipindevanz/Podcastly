package com.pns.podcastly.repo

import com.pns.podcastly.remote.ListenNotesApiService
import com.pns.podcastly.remote.Network
import com.pns.podcastly.remote.model.Podcast


class ListenNotesApiRepo {

    private val apiService = Network.getRetrofit().create(ListenNotesApiService::class.java)

     suspend fun getPodcastsFromServer(): Podcast {
        return apiService.getPodcastsFromServer("a3216e1757f842c5b8579878ebb3110d")
    }
}