package com.sks.mymemo.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memo_table")
data class Memo(
    @PrimaryKey(autoGenerate = true)
    var dateTimeMill: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "memo_title")
    var title: String = "",

    @ColumnInfo(name = "memo_contents")
    var contents : String = ""
)