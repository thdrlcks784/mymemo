package com.sks.mymemo.secretmemo.secretmemolist.passcode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.sks.mymemo.R
import com.sks.mymemo.databinding.FragmentPasswordBinding

class PasswordFragment : Fragment() {

    lateinit var binding : FragmentPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //binding = DataBindingUtil.inflate(inflater,
          //          R.layout.fragment_password, container, false)


        return binding.root
    }

    private fun onDeleteKey(){
        
    }
}