package com.pns.podcastly.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pns.podcastly.database.dao.AudioRecordDao
import com.pns.podcastly.database.model.AudioRecord

@Database(entities = arrayOf(AudioRecord::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun audioRecordDao() : AudioRecordDao
}