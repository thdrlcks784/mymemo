package com.sks.mymemo.allmemo.memolist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sks.mymemo.database.allmemodatabase.MemoDatabaseDao
import java.lang.IllegalArgumentException

class MemoListViewModelFactory(
    private val dataSource: MemoDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory{

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       if(modelClass.isAssignableFrom(MemoListViewModel::class.java)){
           return MemoListViewModel(dataSource,application) as T
       }
       throw IllegalArgumentException("Unknown ViewModel Class")

    }

}
