package com.sks.mymemo.allmemo.memolist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sks.mymemo.database.allmemodatabase.Memo
import com.sks.mymemo.database.MemoCheckBox
import com.sks.mymemo.database.allmemodatabase.MemoDatabaseDao
import kotlinx.coroutines.launch

class MemoListViewModel (
        val memoDatabase: MemoDatabaseDao,
        application: Application) : AndroidViewModel(application){

        private var memoList = MutableLiveData<Memo?>()
        val allMemoList = memoDatabase.getALLMemo()


        init{ initializeMemoList()}

        private fun initializeMemoList(){
                viewModelScope.launch{
                        memoList.value = getMemoListFromDatabase()
                }
        }
        private suspend fun getMemoListFromDatabase(): Memo?{
                var memo = memoDatabase.getLastMemo()
                return memo
        }

        fun deleteMemoList(checkBoxList : ArrayList<MemoCheckBox>, data : List<Memo>){
                for(index in checkBoxList.indices){
                        if(checkBoxList[index].checked){
                                viewModelScope.launch { delete(checkBoxList[index].id) }
                        }
                }
        }


        private suspend fun delete(dateTimeMill : Long){
                memoDatabase.updateToTrashMemo(dateTimeMill,System.currentTimeMillis())
        }



}


