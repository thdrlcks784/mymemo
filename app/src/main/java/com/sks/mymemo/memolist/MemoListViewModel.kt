package com.sks.mymemo.memolist

import android.app.Application
import android.provider.SyncStateContract.Helpers.insert
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sks.mymemo.database.Memo
import com.sks.mymemo.database.MemoDatabaseDao
import kotlinx.coroutines.launch

class MemoListViewModel (
        val database: MemoDatabaseDao,
        application: Application) : AndroidViewModel(application){

        private var memoList = MutableLiveData<Memo?>()

        init{ initializeMemoList() }

        private fun initializeMemoList(){
                viewModelScope.launch{
                        memoList.value = getMemoListFromDatabase()
                }
        }
        private suspend fun getMemoListFromDatabase(): Memo?{
                var memo = database.getLastMemo()
                return memo
        }

        fun onCreateMemo(){
                viewModelScope.launch {
                        val newMemo = Memo()
                        insert(newMemo)
                }
        }

        private suspend fun insert(newMemo: Memo) {
                database.insert(newMemo)
        }

}

