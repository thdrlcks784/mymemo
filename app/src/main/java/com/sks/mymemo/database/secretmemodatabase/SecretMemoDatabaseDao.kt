package com.sks.mymemo.database.secretmemodatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface SecretMemoDatabaseDao {

    @Insert
    suspend fun insert(list : SecretMemo)

    @Update
    suspend fun update(list : SecretMemo)

    @Query("SELECT * from secret_memo_table WHERE dateTimeMill = :key")
    suspend fun get(key: Long): SecretMemo?

    @Query("DELETE FROM secret_memo_table")
    suspend fun clear()

    @Query("SELECT * FROM secret_memo_table ORDER BY dateTimeMill DESC LIMIT 1")
    suspend fun getLastMemo(): SecretMemo?

    @Query("DELETE from secret_memo_table WHERE dateTimeMill = :key")
    suspend fun delete(key: Long)

    @Query("SELECT * FROM secret_memo_table ORDER BY `current_date` DESC")
    fun getALLMemo(): LiveData<List<SecretMemo>>


}