package com.sks.mymemo

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sks.mymemo.database.AnimationFlag
import com.sks.mymemo.database.allmemodatabase.Memo
import com.sks.mymemo.database.MemoCheckBox
import com.sks.mymemo.database.secretmemodatabase.SecretMemo
import java.text.SimpleDateFormat
import java.util.*

class ItemSecretMemoListViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!){
    val title = itemView?.findViewById<TextView>(R.id.memo_list_item_title)
    val date = itemView?.findViewById<TextView>(R.id.memo_list_item_date)
    val contents = itemView?.findViewById<TextView>(R.id.memo_list_item_contents)
    val check = itemView?.findViewById<CheckBox>(R.id.memo_list_item_check)

    fun bind(secretMemo: SecretMemo,checkBox:ArrayList<MemoCheckBox>, position: Int){
        title?.text = secretMemo.title
        date?.text = if(dateCheck(secretMemo.currentDate)) secretMemo.currentTime
        else secretMemo.currentDate + " " +  secretMemo.currentTime
        contents?.text = secretMemo.contents
        checkBoxAnimation(secretMemo)
        if(position>=checkBox.size)checkBox.add(position, MemoCheckBox(secretMemo.dateTimeMill,false))
        check?.isChecked = checkBox[position].checked
    }

    private fun dateCheck(currentDate : String) : Boolean{
        val longNow = System.currentTimeMillis()
        val tDateFormat : String = SimpleDateFormat("yyyy년 MM월 dd일", Locale("Ko","KR")).format(Date(longNow))
        return currentDate == tDateFormat
    }

    private fun checkBoxAnimation(secretMemo: SecretMemo){
        if(secretMemo.isVisibility == AnimationFlag().doneGone){
            check!!.visibility = View.GONE
        }
        else if(secretMemo.isVisibility == AnimationFlag().doSlideOutGone){
            onStartSlideOutAnimation()
            check!!.isChecked = false
            check!!.visibility = View.GONE
            secretMemo.isVisibility = AnimationFlag().doneGone
        }
        else if(secretMemo.isVisibility == AnimationFlag().doSlideInVisible){
            onStartSlideInAnimation()
            check!!.visibility = View.VISIBLE
            secretMemo.isVisibility = AnimationFlag().doneVisible
        }
        else{
            check!!.visibility = View.VISIBLE
        }
    }

    private fun onStartSlideInAnimation(){
        check!!.animation = slideInCheckBox()
        title!!.animation = slideInTitle()
        date!!.animation = slideInTitle()
    }

    private fun onStartSlideOutAnimation(){
        title!!.animation = slideOutTitle()
        date!!.animation = slideOutTitle()
        check!!.animation= slideOutCheckBox()
    }



    private fun slideInTitle() = AnimationUtils.loadAnimation(itemView.context,R.anim.slide_in_left_memo_title)
    private fun slideInCheckBox() = AnimationUtils.loadAnimation(itemView.context,R.anim.slide_in_left_memo_checkbox)

    private fun slideOutTitle() = AnimationUtils.loadAnimation(itemView.context,R.anim.slide_out_left_memo_title)
    private fun slideOutCheckBox() = AnimationUtils.loadAnimation(itemView.context,R.anim.slide_out_left_memo_checkbox)


}


class ItemMemoListViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {


    val title = itemView?.findViewById<TextView>(R.id.memo_list_item_title)
    val date = itemView?.findViewById<TextView>(R.id.memo_list_item_date)
    val contents = itemView?.findViewById<TextView>(R.id.memo_list_item_contents)
    val check = itemView?.findViewById<CheckBox>(R.id.memo_list_item_check)


    fun bind(memo: Memo, checkBox: ArrayList<MemoCheckBox>, position: Int) {
        title?.text = memo.title
        date?.text = if(dateCheck(memo.currentDate)) memo.currentTime
                     else memo.currentDate + " " +  memo.currentTime
        contents?.text = memo.contents
        checkBoxAnimation(memo)
        if(position>=checkBox.size)checkBox.add(position, MemoCheckBox(memo.dateTimeMill,false))
        check?.isChecked = checkBox[position].checked
    }

    private fun dateCheck(currentDate : String) : Boolean{
        val longNow = System.currentTimeMillis()
        val tDateFormat : String = SimpleDateFormat("yyyy년 MM월 dd일", Locale("Ko","KR")).format(Date(longNow))
        return currentDate == tDateFormat
    }




    private fun checkBoxAnimation(memo: Memo){
        if(memo.isVisibility == AnimationFlag().doneGone){
            check!!.visibility = View.GONE
        }
        else if(memo.isVisibility == AnimationFlag().doSlideOutGone){
            onStartSlideOutAnimation()
            check!!.isChecked = false
            check!!.visibility = View.GONE
            memo.isVisibility = AnimationFlag().doneGone
        }
        else if(memo.isVisibility == AnimationFlag().doSlideInVisible){
            onStartSlideInAnimation()
            check!!.visibility = View.VISIBLE
            memo.isVisibility = AnimationFlag().doneVisible
        }
        else{
            check!!.visibility = View.VISIBLE
        }
    }


    private fun onStartSlideInAnimation(){
        check!!.animation = slideInCheckBox()
        title!!.animation = slideInTitle()
        date!!.animation = slideInTitle()
    }

    private fun onStartSlideOutAnimation(){
        title!!.animation = slideOutTitle()
        date!!.animation = slideOutTitle()
        check!!.animation= slideOutCheckBox()
    }



    private fun slideInTitle() = AnimationUtils.loadAnimation(itemView.context,R.anim.slide_in_left_memo_title)
    private fun slideInCheckBox() = AnimationUtils.loadAnimation(itemView.context,R.anim.slide_in_left_memo_checkbox)

    private fun slideOutTitle() = AnimationUtils.loadAnimation(itemView.context,R.anim.slide_out_left_memo_title)
    private fun slideOutCheckBox() = AnimationUtils.loadAnimation(itemView.context,R.anim.slide_out_left_memo_checkbox)

}



class Util() {
    fun hideKeyboard(context : Context, view: View){
        try {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        } catch (e: Exception) {
            // TODO: handle exception
            e.printStackTrace()
        }

    }

    fun showKeyBoard(context : Context){
        try{
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        } catch (e: java.lang.Exception){
            e.printStackTrace()
        }
    }

}