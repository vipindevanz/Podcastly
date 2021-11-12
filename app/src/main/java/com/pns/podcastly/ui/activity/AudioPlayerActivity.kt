package com.pns.podcastly.ui.activity

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.pns.podcastly.R
import kotlinx.android.synthetic.main.activity_audio_player.*

class AudioPlayerActivity : AppCompatActivity() {

    private lateinit var player: MediaPlayer
    private lateinit var runnable: Runnable
    private lateinit var handler: Handler
    private var delay = 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_player)

        player = MediaPlayer()
        val filePath = intent.getStringExtra("filePath")
        val fileName = intent.getStringExtra("fileName")

        player.apply {
            setDataSource(filePath)
            prepare()
        }

        handler = Handler(Looper.getMainLooper())
        runnable = Runnable {
            seekBar.progress = player.currentPosition
            handler.postDelayed(runnable, delay)
        }

        btnPlay.setOnClickListener {
            playPausePlayer()
        }

        playPausePlayer()
        seekBar.max = player.duration

        player.setOnCompletionListener {
            btnPlay.background = ResourcesCompat.getDrawable(resources, R.drawable.ic_play_circle, theme)
            handler.removeCallbacks(runnable)
        }
    }

    private fun playPausePlayer() {
        if (!player.isPlaying){
            player.start()
            btnPlay.background = ResourcesCompat.getDrawable(resources, R.drawable.ic_pause_circle, theme)
            handler.postDelayed(runnable, 0)
        } else{
            player.pause()
            btnPlay.background = ResourcesCompat.getDrawable(resources, R.drawable.ic_play_circle, theme)
            handler.removeCallbacks(runnable)
        }
    }
}