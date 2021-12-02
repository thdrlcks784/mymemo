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

    @Query("DELETE from memo_table WHERE memo_trash_flag == 1")
    suspend fun deleteAll()

    @Query("UPDATE memo_table SET memo_trash_flag = 0, to_trash_date = 0 WHERE dateTimeMill = :key")
    suspend fun updateToCommonMemo(key: Long)

    @Query("UPDATE memo_table SET memo_trash_flag = 1, to_trash_date = :date WHERE dateTimeMill = :key")
    suspend fun updateToTrashMemo(key: Long,date: Long)

    @Query("SELECT * FROM memo_table WHERE memo_trash_flag == 0 ORDER BY `current_date` DESC")
    fun getALLMemo(): LiveData<List<Memo>>

    @Query("SELECT * FROM memo_table WHERE memo_trash_flag == 1 ORDER BY `current_date` DESC")
    fun getALLTrashMemo(): LiveData<List<Memo>>


}