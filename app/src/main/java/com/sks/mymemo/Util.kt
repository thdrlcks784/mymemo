package com.sks.mymemo

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.util.Log
import android.view.View
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sks.mymemo.database.Memo
import java.util.*


class ItemMemoListViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {

    val title = itemView?.findViewById<TextView>(R.id.memo_list_item_title)
    val date = itemView?.findViewById<TextView>(R.id.memo_list_item_date)
    val contents = itemView?.findViewById<TextView>(R.id.memo_list_item_contents)
    val check = itemView?.findViewById<CheckBox>(R.id.memo_list_item_check)

    fun bind(memo: Memo) {
        title?.text = memo.title
        date?.text = memo.currentDate
        contents?.text = memo.contents
        if(memo.isVisibility){
            /*val anim = TranslateAnimation(-check!!.width.toFloat()*1.416f,0f,0f,0f)
            anim.duration = 600
            anim.fillAfter = true
            title!!.animation = anim
            date!!.animation = anim
            check!!.animation = anim*/
            check!!.visibility = View.VISIBLE
        }
        else{
            check!!.visibility = View.GONE
        }
    }
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