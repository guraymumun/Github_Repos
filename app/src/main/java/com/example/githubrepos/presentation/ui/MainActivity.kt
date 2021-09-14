package com.example.githubrepos.presentation.ui

import com.example.githubrepos.R
import com.example.githubrepos.databinding.ActivityMainBinding
import com.example.githubrepos.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {
    private val viewModel: MainViewModel by viewModel()
    override fun getVM(): MainViewModel = viewModel
}