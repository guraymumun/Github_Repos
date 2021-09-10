package com.example.githubrepos.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubrepos.R
import com.example.githubrepos.data.networking.NetworkState
import com.example.githubrepos.databinding.ActivityMainBinding
import com.example.githubrepos.presentation.adapter.RepoRecyclerAdapter
import com.example.githubrepos.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main, ) {
    private lateinit var adapter: RepoRecyclerAdapter
    private var viewManager = LinearLayoutManager(this)
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initRecyclerView()

        viewModel.repoLiveDataList.observe(this) {
            when (it) {
                is NetworkState.Success -> {
                    Log.e("MainActivity", "viewModel.networkState.observe")
                    adapter.submitList(it.data)
                    Log.d("NETWORK_STATUS", "SUCCESS")
                }
                is NetworkState.Error -> {
                    adapter.submitList(it.cachedData)
                    Log.e("NETWORK_STATUS", it.message)
                }
                is NetworkState.Loading -> {
                    Log.i("NETWORK_STATUS", "LOADING")
                }
            }
        }

        binding.addRepo.setOnClickListener { viewModel.insertRandomRepoToDB() }
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = viewManager
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this@MainActivity,
                LinearLayoutManager.VERTICAL
            )
        )
        adapter = RepoRecyclerAdapter(this)
        binding.recyclerView.adapter = adapter;
    }

    override fun getVM(): MainViewModel = viewModel
}