package com.pns.podcastly.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.transformation.FabTransformationSheetBehavior
import com.pns.podcastly.R
import com.pns.podcastly.utils.Constants
import com.pns.podcastly.utils.Timer
import kotlinx.android.synthetic.main.activity_record_audio.*
import kotlinx.android.synthetic.main.bottom_sheet.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RecordAudioActivity : AppCompatActivity(), Timer.OnTimerTickListener {

    private var permissions = arrayOf(Manifest.permission.RECORD_AUDIO)
    private var permissionGranted = false
    private lateinit var recorder: MediaRecorder
    private lateinit var timer: Timer
    private lateinit var vibrator: Vibrator
    private lateinit var amplitudes: ArrayList<Float>
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private var dirPath = ""
    private var fileName = ""
    private var isRecording = false
    private var isPaused = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_audio)

        timer = Timer(this)
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.peekHeight = 0
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        permissionGranted = ActivityCompat.checkSelfPermission(
            this,
            permissions[0]
        ) == PackageManager.PERMISSION_GRANTED

        btnRecord.setOnClickListener {
            when {

                isPaused -> resumeRecorder()
                isRecording -> pauseRecorder()
                else -> startRecording()
            }
            vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
        }

        btnList.setOnClickListener { }

        btnDone.setOnClickListener {
            stopRecorder()
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            bottomSheetBG.visibility = View.VISIBLE
            fileNameInput.setText(fileName)
        }

        btnCancel.setOnClickListener {
            File("$dirPath$fileName.mp3").delete()
            dismiss()
        }

        btnOk.setOnClickListener {
            dismiss()
            save()
        }

        bottomSheetBG.setOnClickListener {
            File("$dirPath$fileName.mp3").delete()
            dismiss()
        }

        btnDelete.setOnClickListener {
            stopRecorder()
            File("$dirPath$fileName.mp3").delete()
        }

        btnDelete.isClickable = false
    }

    private fun save(){
        val newFileName = fileNameInput.text.toString()
        if (fileName != newFileName){
            var newFile = File("$dirPath$newFileName.mp3")
            File("$dirPath$fileName.mp3").renameTo(newFile)
        }
    }

    private fun dismiss() {
        bottomSheetBG.visibility = View.GONE
        hideKeyboard(fileNameInput)

        Handler(Looper.getMainLooper()).postDelayed({
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }, 100)
    }

    private fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == Constants.MICROPHONE_REQUEST_CODE) {
            permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun pauseRecorder() {
        recorder.pause()
        isPaused = true
        timer.pause()
        btnRecord.setImageResource(R.drawable.ic_record)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun resumeRecorder() {
        recorder.resume()
        isPaused = false
        timer.start()
        btnRecord.setImageResource(R.drawable.ic_pause)
    }

    @SuppressLint("SimpleDateFormat")
    private fun startRecording() {
        if (!permissionGranted) {
            ActivityCompat.requestPermissions(this, permissions, Constants.MICROPHONE_REQUEST_CODE)
            return
        }

        recorder = MediaRecorder()
        dirPath = "${externalCacheDir?.absolutePath}/"

        val simpleDateFormat = SimpleDateFormat("yyyy.MM.DD_hh.mm.ss")
        val date: String = simpleDateFormat.format(Date())
        fileName = "audio_record_$date"

        recorder.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile("$dirPath$fileName.mp3")

            try {
                prepare()
                start()
            } catch (e: IOException) {

            }

            btnRecord.setImageResource(R.drawable.ic_pause)
            isRecording = true
            isPaused = false
            timer.start()

            btnDelete.isClickable = true
            btnDelete.setImageResource(R.drawable.ic_delete)

            btnList.visibility = View.GONE
            btnDone.visibility = View.VISIBLE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun stopRecorder() {
        timer.stop()

        recorder.apply {
            stop()
            release()
        }

        isPaused = false
        isRecording = false

        btnList.visibility = View.VISIBLE
        btnDone.visibility = View.GONE

        btnDelete.isClickable = false
        btnDelete.setImageResource(R.drawable.ic_delete_disabled)

        btnRecord.setImageResource(R.drawable.ic_record)

        tvTimer.text = "00:00.00"
        amplitudes = waveFormView.clear()
    }

    override fun onTimerTick(duration: String) {
        tvTimer.text = duration
        waveFormView.addAmplitudes(recorder.maxAmplitude.toFloat())
    }
}