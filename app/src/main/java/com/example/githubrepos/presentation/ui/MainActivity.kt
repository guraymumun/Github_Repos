package com.example.githubrepos.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubrepos.data.networking.NetworkState
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
                    Log.d("SUCCESS", "SUCCESS")
                }
                is NetworkState.Error -> {
                    Log.e("ERROR", "ERROR")
                }
                is NetworkState.Loading -> {
                    Log.i("LOADING", "LOADING")
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