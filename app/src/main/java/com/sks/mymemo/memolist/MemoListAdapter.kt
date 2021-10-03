package com.sks.mymemo.memolist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sks.mymemo.ItemMemoListViewHolder
import com.sks.mymemo.R
import com.sks.mymemo.database.Memo

class MemoListAdapter: RecyclerView.Adapter<ItemMemoListViewHolder>() {
    var data = listOf<Memo>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ItemMemoListViewHolder, position: Int) {
        val item = data[position]
        holder?.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMemoListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_memo_list,parent,false)
        return ItemMemoListViewHolder(view)
    }


}