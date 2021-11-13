package com.pns.podcastly.remote

import com.pns.podcastly.remote.model.ListenNoteResponseDTO
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Header


interface ListenNotesApiService {

    @GET("best_podcasts")
     fun getPodcastsFromServer(@Header("X-ListenAPI-Key") apiKey: String):Observable<ListenNoteResponseDTO>

}