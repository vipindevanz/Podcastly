package com.pns.podcastly.remote

import com.pns.podcastly.remote.model.Podcast
import retrofit2.http.GET
import retrofit2.http.Header


interface ListenNotesApiService {

    @GET("best_podcasts")
     suspend fun getPodcastsFromServer(@Header("X-ListenAPI-Key") apiKey: String): Podcast

}