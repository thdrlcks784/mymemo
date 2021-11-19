package com.sks.mymemo.secretmemo.secretmemolist

import android.content.Context
import android.os.Bundle
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
import com.sks.mymemo.R
import com.sks.mymemo.TempToolbarTitleListener
import com.sks.mymemo.database.AnimationFlag
import com.sks.mymemo.database.allmemodatabase.MemoDatabase
import com.sks.mymemo.databinding.FragmentSecretMemoListBinding
import com.sks.mymemo.allmemo.memolist.MemoListViewModel
import com.sks.mymemo.allmemo.memolist.MemoListViewModelFactory
import com.sks.mymemo.database.secretmemodatabase.SecretMemoDatabase
import kotlinx.android.synthetic.main.fragment_memo_list.*

class SecretMemoListFragment  : Fragment(){

    val adapter = SecretMemoListAdapter()
    lateinit var binding : FragmentSecretMemoListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_secret_memo_list,container,false)
        binding.lifecycleOwner = this

        binding.memoList.adapter = adapter

        //애플리케이션 컨텍스트에 대한 참조를 가져옴
        val application = requireNotNull(this.activity).application
        //DAO에 대한 참조를 통해 데이터 소스에 대한 참조를 가져옴
        val dataSource = SecretMemoDatabase.getInstance(application).secretMemoDatabaseDao
        val secretMemoListViewModelFactory = SecretMemoListViewModelFactory(dataSource,application)

        val secretMemoListViewModel = ViewModelProvider(this, secretMemoListViewModelFactory).get(SecretMemoListViewModel::class.java)

        secretMemoListViewModel.allMemoList.observe(viewLifecycleOwner, Observer{
            it?.let{
                adapter.data = it
            }
        })

        binding.fab.setOnClickListener { view ->
            if (adapter.data.isEmpty()) {
                view.findNavController().navigate(R.id.action_memoListFragment_to_addMemoFragment)
            } else {
                if (adapter.data[0].isVisibility == AnimationFlag().doneVisible) {
                    secretMemoListViewModel.deleteMemoList(adapter.checkBoxList)
                    adapter.notifyDataSetChanged()
                    adapter.checkBoxList.clear()
                    onBackPressedEvent()
                } else {
                    view.findNavController()
                        .navigate(R.id.action_memoListFragment_to_addMemoFragment)
                }
            }
        }


        adapter.setItemLongClickListener(object : SecretMemoListAdapter.ItemLongClickListener{
            override fun itemLongClicked(v: View, position: Int): Boolean {
                if (adapter.data.isNotEmpty()) {
                    if (adapter.data[0].isVisibility  != AnimationFlag().doneVisible) {
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
                item_empty_text.visibility = (if(adapter.itemCount==0) View.VISIBLE else View.GONE)
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
                //fab button ImageResource + 아이콘으로 변경
                binding.fab.setImageResource(R.drawable.ic_add)

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