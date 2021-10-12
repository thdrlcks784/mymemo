package com.sks.mymemo.updatememo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.sks.mymemo.R
import com.sks.mymemo.database.MemoDatabase
import com.sks.mymemo.databinding.FragmentUpdateMemoBinding
import com.sks.mymemo.memolist.MemoListFragmentArgs

class UpdateMemoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentUpdateMemoBinding>(
            inflater, R.layout.fragment_update_memo,container,false)

        //memoListAdapter에서 safe args로 받아온 값
        val args : UpdateMemoFragmentArgs by navArgs()
        val timeMill = args.TimeMill


        //애플리케이션 컨텍스트에 대한 참조를 가져옴
        val application = requireNotNull(this.activity).application
        //DAO에 대한 참조를 통해 데이터 소스에 대한 참조를 가져옴
        val dataSource = MemoDatabase.getInstance(application).memoDatabaseDao
        val viewModelFactory = UpdateMemoViewModelFactory(dataSource,application,timeMill)
        val updateMemoViewModel = ViewModelProvider(this, viewModelFactory).get(UpdateMemoViewModel::class.java)

        updateMemoViewModel._memo.observe(viewLifecycleOwner, Observer{
            it?.let{
                binding.updateMemoTitle.setText(it.title)
                binding.updateMemoContents.setText(it.contents)
            }
        })


        binding.updateMemoViewModel = updateMemoViewModel

        binding.updateMemoFab.setOnClickListener{ view: View ->
            view.findNavController().navigate(R.id.action_updateMemoFragment_to_memoListFragment)
            updateMemoViewModel.onUpdateMemo(binding.updateMemoContents.text.toString(),binding.updateMemoTitle.text.toString(),timeMill)
        }
        binding.lifecycleOwner = this
        //setHasOptionsMenu(true)

        return binding.root
    }
}