package com.sks.mymemo.memolist

import android.app.Application
import android.provider.SyncStateContract.Helpers.insert
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sks.mymemo.database.Memo
import com.sks.mymemo.database.MemoCheckBox
import com.sks.mymemo.database.MemoDatabaseDao
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MemoListViewModel (
        val database: MemoDatabaseDao,
        application: Application) : AndroidViewModel(application){

        private var memoList = MutableLiveData<Memo?>()
        val allMemoList = database.getALLMemo()


        init{ initializeMemoList()}

        private fun initializeMemoList(){
                viewModelScope.launch{
                        memoList.value = getMemoListFromDatabase()
                }
        }
        private suspend fun getMemoListFromDatabase(): Memo?{
                var memo = database.getLastMemo()
                return memo
        }


        private suspend fun insert(newMemo: Memo) {
                database.insert(newMemo)
        }

        fun deleteMemoList(checkBoxList : ArrayList<MemoCheckBox>){
                for(index in checkBoxList.indices){
                        if(checkBoxList[index].checked){
                                Log.d("TAG", "index : $index , flag : ${checkBoxList[index].checked} ,  timeMill : ${checkBoxList[index].id}")
                                viewModelScope.launch { delete(checkBoxList[index].id) }
                        }
                }
        }

        private suspend fun delete(dateTimeMill : Long){
                database.delete(dateTimeMill)
        }



}


