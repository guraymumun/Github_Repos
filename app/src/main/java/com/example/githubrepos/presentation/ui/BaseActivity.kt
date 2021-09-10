package com.example.githubrepos.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.githubrepos.presentation.viewmodel.BaseViewModel

abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel>(
    private val resId: Int,
) : AppCompatActivity() {

    protected lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, resId)
        binding.lifecycleOwner = this

        getVM()?.showLoading?.observe(this) {

        }
    }

    abstract fun getVM(): VM?
}