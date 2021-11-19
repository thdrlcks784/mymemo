package com.sks.mymemo.secretmemo.secretmemolist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sks.mymemo.database.allmemodatabase.Memo
import com.sks.mymemo.database.MemoCheckBox
import com.sks.mymemo.database.allmemodatabase.MemoDatabaseDao
import com.sks.mymemo.database.secretmemodatabase.SecretMemo
import com.sks.mymemo.database.secretmemodatabase.SecretMemoDatabaseDao
import kotlinx.coroutines.launch

class SecretMemoListViewModel  (
    val database: SecretMemoDatabaseDao,
    application: Application
) : AndroidViewModel(application){

    private var secretMemoList = MutableLiveData<SecretMemo?>()
    val allMemoList = database.getALLMemo()


    init{ initializeMemoList()}

    private fun initializeMemoList(){
        viewModelScope.launch{
            secretMemoList.value = getMemoListFromDatabase()
        }
    }
    private suspend fun getMemoListFromDatabase(): SecretMemo? {
        var secretMemo = database.getLastMemo()
        return secretMemo
    }


    private suspend fun insert(newMemo: SecretMemo) {
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