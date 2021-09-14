package com.example.githubrepos.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.githubrepos.presentation.viewmodel.BaseViewModel

abstract class BaseFragment<B: ViewDataBinding, VM : BaseViewModel>(private val resId: Int) : Fragment() {

    protected lateinit var binding: B

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, resId, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    abstract fun getVM(): VM?
}