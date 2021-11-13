package com.pns.podcastly.viewmodel

import androidx.lifecycle.ViewModel
import com.pns.podcastly.remote.model.Podcast
import com.pns.podcastly.repo.ListenNotesApiRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PodcastViewModel() : ViewModel() {
    private val apiRepo = ListenNotesApiRepo()
    private lateinit var data: Podcast
    fun showData(): Podcast {
       return apiRepo.getPodcastsFromServer()
        return data
    }
}