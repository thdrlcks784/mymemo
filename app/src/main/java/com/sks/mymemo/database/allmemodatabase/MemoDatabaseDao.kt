package com.sks.mymemo.database.allmemodatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface MemoDatabaseDao {

    @Insert
    suspend fun insert(list : Memo)

    @Update
    suspend fun update(list : Memo)

    @Query("SELECT * from memo_table WHERE dateTimeMill = :key")
    suspend fun get(key: Long): Memo?

    @Query("DELETE FROM memo_table")
    suspend fun clear()

    @Query("SELECT * FROM memo_table ORDER BY dateTimeMill DESC LIMIT 1")
    suspend fun getLastMemo(): Memo?

    @Query("DELETE from memo_table WHERE dateTimeMill = :key")
    suspend fun delete(key: Long)

    @Query("SELECT * FROM memo_table ORDER BY `current_date` DESC")
    fun getALLMemo(): LiveData<List<Memo>>


}