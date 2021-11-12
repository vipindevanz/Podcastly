package com.pns.podcastly.database.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "audioRecords")
data class AudioRecord(
    var fileName : String,
    var filePath : String,
    var timeStamp : String,
    var duration : String,
    var ampsPath : String
){
    @PrimaryKey(autoGenerate = true)
    var id = 0
    @Ignore
    var isChecked = false
}
