package com.sks.mymemo.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "memo_table")
data class Memo(
    @PrimaryKey(autoGenerate = true)
    var dateTimeMill: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "memo_date")
    var strDate: String = SimpleDateFormat("yyyy년 MM월 dd일 (E)", Locale("Ko","KR")).format(Date(dateTimeMill)),

    @ColumnInfo(name = "memo_time")
    var strTime: String = SimpleDateFormat("kk:mm", Locale("Ko","KR")).format(Date(dateTimeMill)),

    @ColumnInfo(name = "memo_title")
    var title: String,

    @ColumnInfo(name = "memo_contents")
    var contents : String
)