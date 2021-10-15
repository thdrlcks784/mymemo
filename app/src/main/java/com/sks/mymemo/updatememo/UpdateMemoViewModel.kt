package com.sks.mymemo.updatememo

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sks.mymemo.database.Memo
import com.sks.mymemo.database.MemoDatabaseDao
import com.sks.mymemo.memolist.MemoListAdapter
import kotlinx.coroutines.launch

class UpdateMemoViewModel (
    val database: MemoDatabaseDao,
    application: Application,
    timeMill:Long
) : AndroidViewModel(application) {

    var _memo = MutableLiveData<Memo?>()

    fun onUpdateMemo(contents : String, title : String,time : Long){
        viewModelScope.launch {
            val newMemo = Memo(contents = contents,title = title,dateTimeMill = time,currentDateTimeMill = System.currentTimeMillis())
            update(newMemo)
        }
    }
    init{onReCreateMemo(timeMill)}
    fun onReCreateMemo(timeMill : Long){
        viewModelScope.launch {
            get(timeMill)
        }
    }

    private suspend fun get(timeMill: Long){
        _memo.value= database.get(timeMill)!!
        Log.d("TAG", _memo.value!!.contents)
    }

    private suspend fun update(diffMemo: Memo) {
        database.update(diffMemo)
    }


}