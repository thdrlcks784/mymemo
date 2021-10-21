package com.sks.mymemo.memolist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.Toolbar
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sks.mymemo.MainActivity
import com.sks.mymemo.R
import com.sks.mymemo.TempToolbarTitleListener
import com.sks.mymemo.Util
import com.sks.mymemo.database.Memo
import com.sks.mymemo.database.MemoDatabase
import com.sks.mymemo.databinding.FragmentMemoListBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_memo_list.*
import kotlinx.android.synthetic.main.item_memo_list.*

class MemoListFragment : Fragment(){

    val adapter = MemoListAdapter()

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
                (activity as TempToolbarTitleListener).updateTitle("모든 노트 ${adapter.itemCount} 개" )
            }
        })


        return binding.root
    }

    private lateinit var callback: OnBackPressedCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.d("TAG","눌렸다~")
                if(adapter.data[0]?.isVisibility){
                    for(index in 0..adapter.data.size-1){
                        adapter.data[index].isVisibility = false
                    }
                    adapter.notifyDataSetChanged()
                }else{
                    requireActivity().finish()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

}