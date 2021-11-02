package com.sks.mymemo.memolist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sks.mymemo.R
import com.sks.mymemo.TempToolbarTitleListener
import com.sks.mymemo.database.AnimationFlag
import com.sks.mymemo.database.MemoDatabase
import com.sks.mymemo.databinding.FragmentMemoListBinding
import kotlinx.android.synthetic.main.fragment_memo_list.*



class MemoListFragment : Fragment(){

    val adapter = MemoListAdapter()
    lateinit var binding : FragmentMemoListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_memo_list,container,false)
        binding.lifecycleOwner = this



        binding.memoList.adapter = adapter




        //애플리케이션 컨텍스트에 대한 참조를 가져옴
        val application = requireNotNull(this.activity).application
        //DAO에 대한 참조를 통해 데이터 소스에 대한 참조를 가져옴
        val dataSource = MemoDatabase.getInstance(application).memoDatabaseDao
        val viewModelFactory = MemoListViewModelFactory(dataSource,application)

        val memoListViewModel = ViewModelProvider(this, viewModelFactory).get(MemoListViewModel::class.java)

        memoListViewModel.allMemoList.observe(viewLifecycleOwner, Observer{
            it?.let{
                adapter.data = it
            }
        })

        binding.fab.setOnClickListener { view ->
            if (adapter.data.isEmpty()) {
                view.findNavController().navigate(R.id.action_memoListFragment_to_addMemoFragment)
            } else {
                if (adapter.data[0].isVisibility == AnimationFlag().doneVisible) {
                    memoListViewModel.deleteMemoList(adapter.checkBoxList)
                    adapter.notifyDataSetChanged()
                    onBackPressedEvent()
                } else {
                    view.findNavController()
                        .navigate(R.id.action_memoListFragment_to_addMemoFragment)
                }
            }
        }


        adapter.setItemLongClickListener(object : MemoListAdapter.ItemLongClickListener{
            override fun itemLongClicked(v: View, position: Int): Boolean {
                Log.d("TAG", "longClick 실행")
                if (adapter.data.isNotEmpty()) {
                    if (adapter.data[0].isVisibility  !=AnimationFlag().doneVisible) {
                        Log.d("TAG", "ic_delete_outline 실행")
                        binding.fab.setImageResource(R.drawable.ic_delete_outline)
                    }
                }
                return true
            }
        })


        //adapterObserver
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver(){

            override fun onChanged() {
                super.onChanged()
                checkEmpty()
                updateTitle()
                Log.d("TAG","onChanged 실행")
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                checkEmpty()
                Log.d("TAG","onItemRangeInserted 실행")
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                checkEmpty()
                Log.d("TAG","onItemRangeRemoved 실행")
            }
            //item이 없을때 띄워줄 EmptyTextView
            fun checkEmpty(){
                item_empty_text.visibility = (if(adapter.itemCount==0)View.VISIBLE else View.GONE)
            }

            //title 출력
            fun updateTitle(){
                (activity as TempToolbarTitleListener).updateTitle("모든 노트 ${adapter.itemCount} 개" )
            }
        })


        return binding.root
    }

    private fun onBackPressedEvent(){
        if(adapter.data.isNotEmpty()){
            if(adapter.data[0].isVisibility == AnimationFlag().doneGone)requireActivity().finish()
            else{
                binding.fab.setImageResource(R.drawable.ic_add)
                Log.d("TAG","ic_add 실행")
                for(index in adapter.data.indices){
                    adapter.data[index].isVisibility = AnimationFlag().doSlideOutGone
                    adapter.checkBoxList[index].checked = false
                }
                adapter.notifyItemRangeChanged(0,adapter.data.size)
            }
        }
    }

    private lateinit var callback: OnBackPressedCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressedEvent()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

}