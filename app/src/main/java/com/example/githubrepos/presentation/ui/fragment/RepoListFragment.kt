package com.example.githubrepos.presentation.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubrepos.R
import com.example.githubrepos.databinding.RepoListFragmentBinding
import com.example.githubrepos.presentation.adapter.RepoRecyclerAdapter
import com.example.githubrepos.presentation.viewmodel.RepoListViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepoListFragment : BaseFragment<RepoListFragmentBinding, RepoListViewModel>(R.layout.repo_list_fragment) {

    private lateinit var adapter: RepoRecyclerAdapter
    private val viewModel: RepoListViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        lifecycleScope.launchWhenCreated {
            viewModel.requestGetReposWithPaging().collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }

        binding.addRepo.setOnClickListener { viewModel.insertRandomRepoToDB() }
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
        adapter = RepoRecyclerAdapter { repo ->
            Navigation.findNavController(binding.root).navigate(
                RepoListFragmentDirections.actionRepoListFragmentToRepoDetailsFragment(repo)
            )
        }

        binding.recyclerView.adapter = adapter;
    }

    override fun getVM(): RepoListViewModel {
        return viewModel
    }
}