package com.sks.mymemo.database.secretmemodatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SecretMemo::class], version = 1, exportSchema = false)
abstract class SecretMemoDatabase : RoomDatabase() {
    abstract val secretMemoDatabaseDao : SecretMemoDatabaseDao

    companion object{

        @Volatile
        private var INSTANCE: SecretMemoDatabase? = null

        fun getInstance(context: Context): SecretMemoDatabase {
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SecretMemoDatabase::class.java,
                        "secret_memo_list_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance

                }

                return instance
            }
        }

    }
}