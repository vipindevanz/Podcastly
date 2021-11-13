package com.pns.podcastly.viewmodel

import androidx.lifecycle.ViewModel
import com.pns.podcastly.remote.model.ListenNoteResponseDTO
import com.pns.podcastly.repo.ListenNotesApiRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PodcastViewModel() : ViewModel() {
    private val apiRepo = ListenNotesApiRepo()
    private lateinit var data: ListenNoteResponseDTO
    fun showData(): ListenNoteResponseDTO? {
        CoroutineScope(Dispatchers.IO).launch {
            data = apiRepo.getPodcastsFromServer()
        }
        return data
    }
}