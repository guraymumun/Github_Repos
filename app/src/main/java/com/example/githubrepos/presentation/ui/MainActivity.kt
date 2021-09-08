package com.example.githubrepos.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubrepos.data.networking.helper.NetworkState
import com.example.githubrepos.databinding.ActivityMainBinding
import com.example.githubrepos.presentation.adapter.RepoRecyclerAdapter
import com.example.githubrepos.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RepoRecyclerAdapter
    private var viewManager = LinearLayoutManager(this)
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initRecyclerView()

        viewModel.networkState.observe(this) {
            when (it) {
                is NetworkState.Success -> {
                    adapter.submitList(it.data)
                    Log.d("NETWORK_STATUS", "SUCCESS")
                }
                is NetworkState.Error -> {
                    Log.e("NETWORK_STATUS", it.message)
                }
                is NetworkState.Loading -> {
                    Log.i("NETWORK_STATUS", "LOADING")
                }
            }
        }
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
}