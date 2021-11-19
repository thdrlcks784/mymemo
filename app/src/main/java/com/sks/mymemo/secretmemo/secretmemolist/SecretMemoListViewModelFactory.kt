package com.sks.mymemo.secretmemo.secretmemolist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sks.mymemo.allmemo.memolist.MemoListViewModel
import com.sks.mymemo.database.secretmemodatabase.SecretMemoDatabaseDao
import java.lang.IllegalArgumentException

class SecretMemoListViewModelFactory(
    private val dataSource: SecretMemoDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory{

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SecretMemoListViewModel::class.java)){
            return SecretMemoListViewModel(dataSource,application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")

    }

}
