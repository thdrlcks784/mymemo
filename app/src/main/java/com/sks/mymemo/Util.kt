package com.sks.mymemo

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.ColumnInfo
import com.sks.mymemo.database.Memo
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class ItemMemoListViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!){
    val title = itemView?.findViewById<TextView>(R.id.memo_list_item_title)
    val date = itemView?.findViewById<TextView>(R.id.memo_list_item_date)
    val contents = itemView?.findViewById<TextView>(R.id.memo_list_item_contents)

    fun bind(memo: Memo){
        title?.text = memo.title
        //val strDate: String = SimpleDateFormat("yyyy년 MM월 dd일", Locale("Ko","KR")).format(Date(memo.dateTimeMill))
        date?.text = memo.strDate
        contents?.text = memo.contents
    }

}