package com.sks.mymemo.trashmemo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.sks.mymemo.R
import com.sks.mymemo.TempToolbarTitleListener
import com.sks.mymemo.database.AnimationFlag
import com.sks.mymemo.database.allmemodatabase.MemoDatabase
import com.sks.mymemo.databinding.FragmentTrashMemoListBinding
import kotlinx.android.synthetic.main.fragment_memo_list.*

class TrashMemoFragment : Fragment(){

    val adapter = TrashMemoAdapter()
    lateinit var binding : FragmentTrashMemoListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_trash_memo_list,container,false)
        binding.lifecycleOwner = this

        binding.memoList.adapter = adapter

        //애플리케이션 컨텍스트에 대한 참조를 가져옴
        val application = requireNotNull(this.activity).application
        //DAO에 대한 참조를 통해 데이터 소스에 대한 참조를 가져옴
        val dataSource = MemoDatabase.getInstance(application).memoDatabaseDao
        val viewModelFactory = TrashMemoViewModelFactory(dataSource,application)

        val trashMemoListViewModel = ViewModelProvider(this, viewModelFactory).get(TrashMemoViewModel::class.java)

        trashMemoListViewModel.allMemoList.observe(viewLifecycleOwner, Observer{
            it?.let{
                adapter.data = it
            }
        })


        adapter.setItemLongClickListener(object : TrashMemoAdapter.ItemLongClickListener{
            override fun itemLongClicked(v: View, position: Int): Boolean {
                if (adapter.data.isNotEmpty()) {
                    if (adapter.data[0].isVisibility  != AnimationFlag().doneVisible) {
                        binding.buttonPanel.isVisible = true
                    }
                }
                return true
            }
        })

        binding.buttonEmptyTrash.setOnClickListener {
            trashMemoListViewModel.deleteAllMemoList()
            onBackPressedEvent()
        }

        binding.buttonDeleteSeleted.setOnClickListener{
            trashMemoListViewModel.deleteMemoList(adapter.checkBoxList)
            onBackPressedEvent()
        }

        binding.buttonRecoverySeleted.setOnClickListener{
            trashMemoListViewModel.recoveryMemoList(adapter.checkBoxList)
            onBackPressedEvent()
        }




        //adapterObserver
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver(){

            override fun onChanged() {
                super.onChanged()
                checkEmpty()
                updateTitle()
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                checkEmpty()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                checkEmpty()
            }
            //item이 없을때 띄워줄 EmptyTextView
            fun checkEmpty(){
                item_empty_text.visibility = (if(adapter.itemCount==0)View.VISIBLE else View.GONE)
            }

            //title 출력
            fun updateTitle(){
                (activity as TempToolbarTitleListener).updateTitle("휴지통 :  ${adapter.itemCount} 개" )
            }
        })


        return binding.root
    }

    private fun onBackPressedEvent(){
        if(adapter.data.isNotEmpty()){
            if(adapter.data[0].isVisibility == AnimationFlag().doneGone)requireActivity().finish()
            else{

                binding.buttonPanel.isVisible = false

                //item slideOut animation 실행
                for(index in adapter.data.indices){
                    adapter.data[index].isVisibility = AnimationFlag().doSlideOutGone
                }

                //checkBox check 전체해제
                for(index in adapter.checkBoxList.indices){
                    adapter.checkBoxList[index].checked = false
                }
                adapter.notifyItemRangeChanged(0,adapter.data.size)
            }
        }
        else{
            requireActivity().finish()
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