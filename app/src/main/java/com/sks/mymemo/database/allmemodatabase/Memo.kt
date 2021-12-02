package com.sks.mymemo.database.allmemodatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sks.mymemo.database.MemoTrashFlag
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "memo_table")
data class Memo(
    @PrimaryKey(autoGenerate = true)
    val dateTimeMill: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "first_created_date")
    val firstCreatedDate: String = SimpleDateFormat("yyyy년 MM월 dd일", Locale("Ko","KR")).format(Date(dateTimeMill)),

    @ColumnInfo(name = "first_created_time")
    val firstCreatedTime: String = SimpleDateFormat("kk:mm:ss (E)" , Locale("Ko","KR")).format(Date(dateTimeMill)),

    @ColumnInfo(name = "current_date_timeMill")
    val currentDateTimeMill: Long = dateTimeMill,

    @ColumnInfo(name = "current_date")
    var currentDate: String = SimpleDateFormat("yyyy년 MM월 dd일", Locale("Ko","KR")).format(Date(currentDateTimeMill)),

    @ColumnInfo(name = "current_time")
    var currentTime: String = SimpleDateFormat("kk:mm:ss (E)",Locale("Ko","KR")).format(Date(currentDateTimeMill)),

    @ColumnInfo(name = "memo_title")
    var title: String,

    @ColumnInfo(name = "memo_contents")
    var contents : String,

    @ColumnInfo(name = "memo_isVisibility")
    var isVisibility : Int = 0,

    @ColumnInfo(name = "memo_trash_flag")
    var memoTrashFrag : Int = MemoTrashFlag().commonMemo,

    @ColumnInfo(name = "to_trash_date")
    val toTrashDate : Long = 0
)