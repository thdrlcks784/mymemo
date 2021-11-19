package com.sks.mymemo.allmemo.addmemo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sks.mymemo.database.allmemodatabase.MemoDatabaseDao
import java.lang.IllegalArgumentException

class AddMemoViewModelFactory (private val dataSource: MemoDatabaseDao,
                               private val application: Application
) : ViewModelProvider.Factory{

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AddMemoViewModel::class.java)){
            return AddMemoViewModel(dataSource,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")

    }

}