package com.sks.mymemo.allmemo.updatememo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sks.mymemo.database.allmemodatabase.MemoDatabaseDao
import java.lang.IllegalArgumentException

class UpdateMemoViewModelFactory (private val dataSource: MemoDatabaseDao,
                                  private val application: Application,
                                  private var timeMill: Long
) : ViewModelProvider.Factory{

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UpdateMemoViewModel::class.java)){
            return UpdateMemoViewModel(dataSource,application,timeMill) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")

    }

}