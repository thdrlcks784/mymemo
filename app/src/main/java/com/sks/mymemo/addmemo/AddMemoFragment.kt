package com.sks.mymemo.addmemo


import android.app.Activity
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.sks.mymemo.R
import com.sks.mymemo.Util
import com.sks.mymemo.database.MemoDatabase
import com.sks.mymemo.databinding.FragmentAddMemoBinding



class AddMemoFragment : Fragment(){



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentAddMemoBinding>(
            inflater, R.layout.fragment_add_memo,container,false)
        //util
        val util = Util()

        //애플리케이션 컨텍스트에 대한 참조를 가져옴
        val application = requireNotNull(this.activity).application
        //DAO에 대한 참조를 통해 데이터 소스에 대한 참조를 가져옴
        val dataSource = MemoDatabase.getInstance(application).memoDatabaseDao
        val viewModelFactory = AddMemoViewModelFactory(dataSource,application)
        val addMemoViewModel = ViewModelProvider(this, viewModelFactory).get(AddMemoViewModel::class.java)

        binding.addMemoViewModel = addMemoViewModel

        binding.addMemoFab.setOnClickListener{ view: View ->
            view.findNavController().navigate(R.id.action_addMemoFragment_to_memoListFragment)
            addMemoViewModel.onCreateMemo(binding.addMemoContents.text.toString(),binding.addMemoTitle.text.toString())
            context?.let { util.hideKeyboard(it,view) }
        }
        binding.lifecycleOwner = this
        //setHasOptionsMenu(true)

        return binding.root
    }


    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return NavigationUI.onNavDestinationSelected(item,requireView().findNavController()) || super.onOptionsItemSelected(item)
    }*/
}