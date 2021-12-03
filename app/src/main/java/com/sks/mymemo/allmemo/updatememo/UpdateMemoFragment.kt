package com.sks.mymemo.allmemo.updatememo

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
import com.sks.mymemo.TempToolbarTitleListener
import com.sks.mymemo.Util
import com.sks.mymemo.database.allmemodatabase.MemoDatabase
import com.sks.mymemo.databinding.FragmentUpdateMemoBinding

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

        //util
        val util = Util()

        //애플리케이션 컨텍스트에 대한 참조를 가져옴
        val application = requireNotNull(this.activity).application
        //DAO에 대한 참조를 통해 데이터 소스에 대한 참조를 가져옴
        val dataSource = MemoDatabase.getInstance(application).memoDatabaseDao
        val viewModelFactory = UpdateMemoViewModelFactory(dataSource,application,timeMill)
        val updateMemoViewModel = ViewModelProvider(this, viewModelFactory).get(UpdateMemoViewModel::class.java)

        var preTitle : String = ""
        var preContents : String = ""

        updateMemoViewModel._memo.observe(viewLifecycleOwner, Observer{
            it?.let{
                preTitle = it.title
                preContents = it.contents
                binding.updateMemoTitle.setText(preTitle)
                binding.updateMemoContents.setText(preContents)
                (activity as TempToolbarTitleListener).updateTitle("${it.firstCreatedDate} 생성" )
            }
        })




        binding.updateMemoViewModel = updateMemoViewModel

        binding.updateMemoFab.setOnClickListener{ view: View ->
            view.findNavController().navigate(R.id.action_updateMemoFragment_to_memoListFragment)
            if(!(binding.updateMemoContents.text.toString().equals(preContents)&&binding.updateMemoTitle.text.toString().equals(preTitle))){
                updateMemoViewModel.onUpdateMemo(binding.updateMemoContents.text.toString(),binding.updateMemoTitle.text.toString(),timeMill)
            }
            context?.let{ util.hideKeyboard(it,view) }
        }
        binding.lifecycleOwner = this

        return binding.root
    }
}