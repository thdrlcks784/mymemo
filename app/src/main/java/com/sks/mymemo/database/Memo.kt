package com.sks.mymemo.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "memo_table")
data class Memo(
    @PrimaryKey(autoGenerate = true)
    val dateTimeMill: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "first_created_date")
    val firstCreatedDate: String = SimpleDateFormat("yyyy년 MM월 dd일 kk:mm:ss (E)", Locale("Ko","KR")).format(Date(dateTimeMill)),

    @ColumnInfo(name = "current_date")
    val currentDateTimeMill: Long = dateTimeMill,

    @ColumnInfo(name = "memo_date")
    var currentDate: String = SimpleDateFormat("yyyy년 MM월 dd일 kk:mm:ss (E)", Locale("Ko","KR")).format(Date(currentDateTimeMill)),

    @ColumnInfo(name = "memo_title")
    var title: String,

    @ColumnInfo(name = "memo_contents")
    var contents : String,

    @ColumnInfo(name = "memo_isVisibility")
    var isVisibility : Int = 0
)