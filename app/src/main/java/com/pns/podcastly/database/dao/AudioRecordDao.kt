package com.pns.podcastly.database.dao

import androidx.room.*
import com.pns.podcastly.database.model.AudioRecord

@Dao
interface AudioRecordDao {

    @Query("select * from audioRecords")
    fun getAll(): List<AudioRecord>

    @Insert
    fun insert(vararg audioRecord: AudioRecord)

    @Delete
    fun delete(audioRecord: AudioRecord)

    @Delete
    fun delete(audioRecord: Array<AudioRecord>)

    @Update
    fun update(audioRecord: AudioRecord)
}