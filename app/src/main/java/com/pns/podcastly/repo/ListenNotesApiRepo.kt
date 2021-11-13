package com.pns.podcastly.repo

import android.util.Log
import com.pns.podcastly.remote.ListenNotesApiService
import com.pns.podcastly.remote.NetWork
import com.pns.podcastly.remote.model.ListenNoteResponseDTO

class ListenNotesApiRepo {

    private val apiService = NetWork.getRetrofit().create(ListenNotesApiService::class.java)

    suspend fun getPodcastsFromServer(): ListenNoteResponseDTO {
        val res = apiService.getPodcastsFromServer("a3216e1757f842c5b8579878ebb3110d")
        Log.i("rkpsx7", "${res.podcasts[0].country}")
        Log.i("rkpsx7", "${res.podcasts.size}")
        return res
    }
}