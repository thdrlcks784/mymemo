package com.sks.mymemo.memolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sks.mymemo.R
import com.sks.mymemo.database.MemoDatabase
import com.sks.mymemo.databinding.FragmentMemoListBinding
import kotlinx.android.synthetic.main.fragment_memo_list.*

class MemoListFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentMemoListBinding>(
            inflater, R.layout.fragment_memo_list,container,false)

        binding.setLifecycleOwner(this)

        //floating buttion click listener
        binding.fab.setOnClickListener{ view: View ->
            view.findNavController().navigate(R.id.action_memoListFragment_to_addMemoFragment)
        }


        //item이 없을때 띄워줄 EmptyTextView
        val adapter = MemoListAdapter()
        binding.itemEmptyText.visibility = View.GONE
        binding.memoList.adapter = adapter
        //item이 없거나 없어졌을때 EmptyTextView를 띄우기 위함
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver(){

            override fun onChanged() {
                super.onChanged()
                checkEmpty()
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


        })



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

        binding.memoListViewModel = memoListViewModel

        return binding.root
    }

}