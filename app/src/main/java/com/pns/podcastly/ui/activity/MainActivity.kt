package com.pns.podcastly.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.pns.podcastly.R
import com.pns.podcastly.remote.model.ListenNoteResponseDTO
import com.pns.podcastly.repo.ListenNotesApiRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    val podcastList = mutableListOf<ListenNoteResponseDTO>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lst = ListenNotesApiRepo()

        CoroutineScope(IO).launch {
            val response = lst.getPodcastsFromServer()
            val podcasts = response.podcasts as ArrayList<ListenNoteResponseDTO>
            podcastList.clear()
            podcastList.addAll(podcasts)
            Log.i("rkpsx7", "onCreate: ${podcastList.size}")
        }


    }
}