package com.sks.mymemo

import android.content.Context
import android.graphics.drawable.Animatable2
import android.util.Log
import android.view.View
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sks.mymemo.database.AnimationFlag
import com.sks.mymemo.database.Memo
import com.sks.mymemo.database.MemoCheckBox
import com.sks.mymemo.databinding.FragmentMemoListBinding
import java.util.*


class ItemMemoListViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {


    val title = itemView?.findViewById<TextView>(R.id.memo_list_item_title)
    val date = itemView?.findViewById<TextView>(R.id.memo_list_item_date)
    val contents = itemView?.findViewById<TextView>(R.id.memo_list_item_contents)
    val check = itemView?.findViewById<CheckBox>(R.id.memo_list_item_check)



    fun bind(memo: Memo, checkBox: ArrayList<MemoCheckBox>, position : Int) {
        title?.text = memo.title
        date?.text = memo.currentDate
        contents?.text = memo.contents
        checkBoxAnimation(memo)
        if(position>=checkBox.size)checkBox.add(position, MemoCheckBox(memo.dateTimeMill,false))
        check?.isChecked = checkBox[position].checked
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