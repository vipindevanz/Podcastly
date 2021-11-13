package com.pns.podcastly.repo

import com.pns.podcastly.remote.ListenNotesApiService
import com.pns.podcastly.remote.Network
import com.pns.podcastly.remote.model.ListenNoteResponseDTO
import io.reactivex.rxjava3.core.Observable


class ListenNotesApiRepo {

    private val apiService = Network.getRetrofit().create(ListenNotesApiService::class.java)

    fun getPodcastsFromServer(): Observable<ListenNoteResponseDTO> {
        return apiService.getPodcastsFromServer("a3216e1757f842c5b8579878ebb3110d")
    }
}