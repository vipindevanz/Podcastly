package com.pns.podcastly.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.net.Uri
import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.room.Room
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.pns.podcastly.R
import com.pns.podcastly.database.db.AppDatabase
import com.pns.podcastly.database.model.AudioRecord
import com.pns.podcastly.utils.Constants
import com.pns.podcastly.utils.Timer
import kotlinx.android.synthetic.main.activity_record_audio.*
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import android.provider.MediaStore
import android.provider.OpenableColumns
import androidx.loader.content.CursorLoader

@DelicateCoroutinesApi
class RecordAudioActivity : AppCompatActivity(), Timer.OnTimerTickListener {

    private var permissions = arrayOf(Manifest.permission.RECORD_AUDIO)
    private var permissionGranted = false
    private lateinit var recorder: MediaRecorder
    private lateinit var timer: Timer
    private lateinit var db: AppDatabase
    private lateinit var vibrator: Vibrator
    private lateinit var amplitudes: ArrayList<Float>
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private var dirPath = ""
    private var fileName = ""
    private var duration = ""
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

        db = Room.databaseBuilder(this, AppDatabase::class.java, "audioRecords").build()

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

        btnList.setOnClickListener {
            startActivity(Intent(this, GalleryActivity::class.java))
        }

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

        back.setOnClickListener { onBackPressed() }
        openGallery.setOnClickListener { openGallery() }

        btnDelete.isClickable = false
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "audio/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, Constants.MUSIC_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.MUSIC_REQUEST_CODE && data?.data != null) {
            val filePath = getRealPathFromURI(data.data!!)
            val fileName = getFileName(data.data!!)

            val intent = Intent(this@RecordAudioActivity, AudioPlayerActivity::class.java)
            intent.putExtra("filePath", filePath)
            intent.putExtra("fileName", fileName)
            intent.putExtra("uri", data.data.toString())
            startActivity(intent)
        }
    }

    private fun getRealPathFromURI(contentUri: Uri): String? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val loader = CursorLoader(this, contentUri, proj, null, null, null)
        val cursor = loader.loadInBackground()
        val columnIndex = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val result = cursor.getString(columnIndex)
        cursor.close()
        return result
    }

    @SuppressLint("Range")
    private fun getFileName(uri: Uri): String? {

        if (uri.scheme == "content") {
            val cursor = this.contentResolver.query(uri, null, null, null, null)
            cursor.use {
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        return cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    }
                }
            }
        }

        return uri.path?.substring(uri.path!!.lastIndexOf('/') + 1)
    }

    private fun save() {
        val newFileName = fileNameInput.text.toString()
        if (fileName != newFileName) {
            val newFile = File("$dirPath$newFileName.mp3")
            File("$dirPath$fileName.mp3").renameTo(newFile)
        }

        val filePath = "$dirPath$newFileName.mp3"
        val timeStamp = Date().time
        val ampsPath = "$dirPath$newFileName"

        try {
            val fos = FileOutputStream(ampsPath)
            val out = ObjectOutputStream(fos)
            out.writeObject(amplitudes)
            fos.close()
            out.close()
        } catch (e: IOException) {
            Log.d(Constants.DEBUG_TAG, "error ${e.message}")
        }

        val record = AudioRecord(newFileName, filePath, timeStamp.toString(), duration, ampsPath)

        CoroutineScope(Dispatchers.IO).launch {
            db.audioRecordDao().insert(record)
            runOnUiThread {
                Toast.makeText(
                    this@RecordAudioActivity,
                    "Saved recording",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun dismiss() {
        bottomSheetBG.visibility = View.GONE
        hideKeyboard(fileNameInput)

        Handler(Looper.getMainLooper()).postDelayed({
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }, 500)
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
            startRecording()
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
        this.duration = duration.dropLast(3)
        waveFormView.addAmplitudes(recorder.maxAmplitude.toFloat())
    }
}