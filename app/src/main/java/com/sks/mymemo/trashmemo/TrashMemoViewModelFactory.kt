package com.sks.mymemo.trashmemo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sks.mymemo.database.allmemodatabase.MemoDatabaseDao
import java.lang.IllegalArgumentException

class TrashMemoViewModelFactory (
    private val dataSource: MemoDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory{

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TrashMemoViewModel::class.java)){
            return TrashMemoViewModel(dataSource,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")

    }

}