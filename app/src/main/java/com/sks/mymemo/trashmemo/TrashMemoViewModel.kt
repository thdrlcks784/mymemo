package com.sks.mymemo.trashmemo

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sks.mymemo.database.MemoCheckBox
import com.sks.mymemo.database.allmemodatabase.Memo
import com.sks.mymemo.database.allmemodatabase.MemoDatabaseDao
import kotlinx.coroutines.launch

class TrashMemoViewModel (
    val database: MemoDatabaseDao,
    application: Application
) : AndroidViewModel(application){

        private var memoList = MutableLiveData<Memo?>()
        val allMemoList = database.getALLTrashMemo()


        init{ initializeMemoList()}

        private fun initializeMemoList(){
            viewModelScope.launch{
                var tempMemo = getMemoListFromDatabase()
                if (tempMemo != null) {
                    if(System.currentTimeMillis()-tempMemo.toTrashDate<1296000000)memoList.value = getMemoListFromDatabase()
                    else delete(tempMemo.dateTimeMill)
                }
            }
        }
        private suspend fun getMemoListFromDatabase(): Memo?{
            var trashMemo = database.getLastMemo()
            return trashMemo
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

        fun deleteAllMemoList(){
            viewModelScope.launch { deleteAll() }
        }

        fun recoveryMemoList(checkBoxList: ArrayList<MemoCheckBox>){
            for(index in checkBoxList.indices){
                if(checkBoxList[index].checked){
                    viewModelScope.launch { recovery(checkBoxList[index].id) }
                }
            }
        }


        private suspend fun delete(dateTimeMill : Long){
            database.delete(dateTimeMill)
        }

        private suspend fun deleteAll(){
            database.deleteAll()
        }

        private suspend fun recovery(dateTimeMill: Long){
            database.updateToCommonMemo(dateTimeMill)
        }



}