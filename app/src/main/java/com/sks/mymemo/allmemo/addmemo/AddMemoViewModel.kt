package com.sks.mymemo.allmemo.addmemo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sks.mymemo.database.allmemodatabase.Memo
import com.sks.mymemo.database.allmemodatabase.MemoDatabaseDao
import kotlinx.coroutines.launch

class AddMemoViewModel(
    val database: MemoDatabaseDao,
    application: Application
    ) : AndroidViewModel(application) {



    fun onCreateMemo(contents : String, title : String){
        viewModelScope.launch {
            if(contents.length!=0){
                if(title.length==0){
                    val newMemo = Memo(contents = contents,title = "제목없음")
                    insert(newMemo)
                }
                else{
                    val newMemo = Memo(contents = contents,title = title)
                    insert(newMemo)
                }
            }

        }
    }



    private suspend fun insert(newMemo: Memo) {
        database.insert(newMemo)
    }


}