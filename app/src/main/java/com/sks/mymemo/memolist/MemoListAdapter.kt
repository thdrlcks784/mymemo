package com.sks.mymemo.memolist


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sks.mymemo.ItemMemoListViewHolder
import com.sks.mymemo.R
import com.sks.mymemo.database.AnimationFlag
import com.sks.mymemo.database.Memo
import com.sks.mymemo.database.MemoCheckBox


class MemoListAdapter: RecyclerView.Adapter<ItemMemoListViewHolder>() {

    interface ItemLongClickListener{
        fun itemLongClicked(v:View,position: Int) : Boolean
    }

    private lateinit var listener:ItemLongClickListener

    fun setItemLongClickListener(listener: ItemLongClickListener){
        this.listener = listener
    }


    var data = listOf<Memo>()
        set(value){
            field = value
            notifyDataSetChanged()
        }
    var checkBoxList = arrayListOf<MemoCheckBox>()


    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ItemMemoListViewHolder, position: Int) {
        val item = data[position]
        holder?.bind(item,checkBoxList,position)



        holder.itemView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if(data[0].isVisibility== AnimationFlag().doneVisible){
                    checkBoxList[position].checked = !holder.check!!.isChecked
                    holder.check!!.isChecked = !holder.check!!.isChecked
                }else{
                    val memoTimeMill = item.dateTimeMill
                    val action = MemoListFragmentDirections.actionMemoListFragmentToUpdateMemoFragment(memoTimeMill)
                    v!!.findNavController().navigate(action)
                }

            }
        })

        holder.itemView.setOnLongClickListener {
            if(data[0].isVisibility == AnimationFlag().doneVisible){
                holder.check!!.isChecked = !holder.check!!.isChecked
            } else{
                for(index in 0..data.size-1){
                    data[index].isVisibility = AnimationFlag().doSlideInVisible
                }
                notifyItemRangeChanged(0,data.size)
            }
            listener.itemLongClicked(it,position)
        }

        /*holder.itemView.setOnLongClickListener(object : View.OnLongClickListener{
            override fun onLongClick(v: View?): Boolean {


                return true
            }
        })*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMemoListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_memo_list,parent,false)
        return ItemMemoListViewHolder(view)
    }



}