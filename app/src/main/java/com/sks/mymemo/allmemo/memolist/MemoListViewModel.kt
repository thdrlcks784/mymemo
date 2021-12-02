package com.sks.mymemo.allmemo.memolist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sks.mymemo.database.allmemodatabase.Memo
import com.sks.mymemo.database.MemoCheckBox
import com.sks.mymemo.database.MemoTrashFlag
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


        private suspend fun insert(newMemo: Memo) {
                memoDatabase.insert(newMemo)
        }

        fun deleteMemoList(checkBoxList : ArrayList<MemoCheckBox>, data : List<Memo>){
                for(index in checkBoxList.indices){
                        if(checkBoxList[index].checked){
                                //data[index].memoTrashFrag = MemoTrashFlag().trashMemo
                                //Log.d("TAG", "index : $index , flag : ${checkBoxList[index].checked} ,  timeMill : ${checkBoxList[index].id} , trashFlag : ${data[index].memoTrashFrag}")
                                viewModelScope.launch { delete(checkBoxList[index].id) }
                        }
                }
        }


        private suspend fun delete(dateTimeMill : Long){
                memoDatabase.updateToTrashMemo(dateTimeMill,System.currentTimeMillis())
        }



}


