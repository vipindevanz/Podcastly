package com.pns.podcastly.ui.activity

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.media.MediaPlayer
import android.media.PlaybackParams
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.pns.podcastly.R
import com.pns.podcastly.database.model.Podcast
import com.pns.podcastly.utils.Constants
import kotlinx.android.synthetic.main.activity_audio_player.*
import java.text.DecimalFormat
import java.text.NumberFormat

class AudioPlayerActivity : AppCompatActivity() {

    private lateinit var player: MediaPlayer
    private lateinit var runnable: Runnable
    private lateinit var handler: Handler
    private lateinit var dialog: ProgressDialog
    private var delay = 1000L
    private var jumpValue = 5000
    private var playBackSpeed = 1.0f

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_player)

        val filePath = intent.getStringExtra("filePath")
        val fileName = intent.getStringExtra("fileName")
        val uriString = intent.getStringExtra("uri")

        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolBar.setNavigationOnClickListener { onBackPressed() }

        tvFileName.text = fileName

        dialog = ProgressDialog(this)
        player = MediaPlayer()
        player.apply {
            setDataSource(filePath)
            prepare()
        }

        tvTrackDuration.text = dateFormat(player.duration)

        handler = Handler(Looper.getMainLooper())
        runnable = Runnable {
            seekBar.progress = player.currentPosition
            tvTrackProgress.text = dateFormat(player.currentPosition)
            handler.postDelayed(runnable, delay)
        }

        btnPlay.setOnClickListener {
            playPausePlayer()
        }

        playPausePlayer()
        seekBar.max = player.duration

        player.setOnCompletionListener {
            btnPlay.background =
                ResourcesCompat.getDrawable(resources, R.drawable.ic_play_circle, theme)
            handler.removeCallbacks(runnable)
        }

        btnForward.setOnClickListener {
            player.seekTo(player.currentPosition + jumpValue)
            seekBar.progress += jumpValue
        }

        btnBackward.setOnClickListener {
            player.seekTo(player.currentPosition - jumpValue)
            seekBar.progress -= jumpValue
        }

        upload.setOnClickListener {
            if (uriString != null) {
                uploadPodcast(uriString)
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
            }
        }

        chip.setOnClickListener {
            if (playBackSpeed != 2.0f) {
                playBackSpeed += 0.5f
            } else {
                playBackSpeed = 0.5f
            }
            player.playbackParams = PlaybackParams().setSpeed(playBackSpeed)
            chip.text = "x $playBackSpeed"
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) player.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    private fun uploadPodcast(uriString: String) {

        dialog.setMessage("Uploading")
        dialog.show()

        val uri = Uri.parse(uriString)
        val storageRef = uri.lastPathSegment?.let {
            Firebase.storage.reference.child("Podcasts").child(
                it
            )
        }

        storageRef?.putFile(uri)?.addOnSuccessListener { it ->

            val uriTask = it.storage.downloadUrl
            while (!uriTask.isComplete) {
                Log.d(Constants.DEBUG_TAG, "uploading...")
            }
            val newUri = uriTask.result
            val podcastUrl = newUri.toString()
            val ref = FirebaseDatabase.getInstance().getReference("podcasts")
            val key : String = ref.push().key.toString()

            ref.child(key).setValue(Podcast(podcastUrl))
                .addOnSuccessListener {
                    Toast.makeText(applicationContext, "Uploaded", Toast.LENGTH_LONG).show()
                    dialog.cancel()
                }.addOnFailureListener {
                    Log.d(Constants.DEBUG_TAG, "${it.message}")
                    dialog.dismiss()
                }

        }?.addOnFailureListener {
            dialog.cancel()
        }
    }

    private fun playPausePlayer() {

        if (!player.isPlaying) {
            player.start()
            btnPlay.background =
                ResourcesCompat.getDrawable(resources, R.drawable.ic_pause_circle, theme)
            handler.postDelayed(runnable, delay)
        } else {
            player.pause()
            btnPlay.background =
                ResourcesCompat.getDrawable(resources, R.drawable.ic_play_circle, theme)
            handler.removeCallbacks(runnable)
        }
    }

    private fun dateFormat(duration: Int): String {
        val d = duration / 1000
        val s = d % 60
        val m = (d / 60 % 60)
        val h = ((d - m * 60) / 360)

        val f: NumberFormat = DecimalFormat("00")
        var str = "$m:${f.format(s)}"

        if (h > 0) str = "$h:$str"
        return str
    }

    override fun onBackPressed() {
        super.onBackPressed()
        player.stop()
        player.release()
        handler.removeCallbacks(runnable)
    }
}