package com.sks.mymemo.memolist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
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

        holder.itemView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if(data[0].isVisibility){
                    holder.check!!.isChecked = !holder.check!!.isChecked
                }else{
                    val memoTimeMill = item.dateTimeMill
                    val action = MemoListFragmentDirections.actionMemoListFragmentToUpdateMemoFragment(memoTimeMill)
                    v!!.findNavController().navigate(action)
                }

            }
        })

        holder.itemView.setOnLongClickListener(object : View.OnLongClickListener{
            override fun onLongClick(v: View?): Boolean {
                if(data[0].isVisibility){
                    holder.check!!.isChecked = !holder.check!!.isChecked
                } else{
                    for(index in 0..data.size-1){
                        data[index].isVisibility = true
                    }
                    notifyDataSetChanged()
                }
                return true
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMemoListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_memo_list,parent,false)
        return ItemMemoListViewHolder(view)
    }


}