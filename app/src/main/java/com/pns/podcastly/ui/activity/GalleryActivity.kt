package com.pns.podcastly.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.pns.podcastly.R
import com.pns.podcastly.database.db.AppDatabase
import com.pns.podcastly.database.model.AudioRecord
import com.pns.podcastly.interfaces.OnItemClickListener
import com.pns.podcastly.ui.adapter.AudioAdapter
import kotlinx.android.synthetic.main.activity_gallery.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class GalleryActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var records: ArrayList<AudioRecord>
    private lateinit var adapter: AudioAdapter
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        records = ArrayList()

        db = Room.databaseBuilder(this, AppDatabase::class.java, "audioRecords").build()
        adapter = AudioAdapter(records, this)

        recyclerView.adapter = adapter

        fetchAll()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchAll() {

        GlobalScope.launch {
            records.clear()
            val queryResults = db.audioRecordDao().getAll()
            records.addAll(queryResults)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onItemClickListener(position: Int) {
        Toast.makeText(this, "ok", Toast.LENGTH_LONG).show()
        val audioRecord = records[position]
        val intent = Intent(this, AudioPlayerActivity::class.java)
        intent.putExtra("filePath", audioRecord.filePath)
        intent.putExtra("fileName", audioRecord.fileName)
        startActivity(intent)
    }

    override fun onItemLongClickListener(position: Int) {
        Toast.makeText(this, "ok", Toast.LENGTH_LONG).show()
    }
}