package com.example.githubrepos.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepos.data.networking.NetworkState
import com.example.githubrepos.data.repositories.GithubRepoRepository
import com.example.githubrepos.domain.model.Repo
import kotlinx.coroutines.launch

class MainViewModel(private val githubRepoRepository: GithubRepoRepository) : ViewModel() {
    private var _list = MutableLiveData<List<Repo>>(arrayListOf())
    val list: LiveData<List<Repo>> get() = _list
    val networkState = MutableLiveData<NetworkState<List<Repo>>>()

    init {
        requestGetRepos()
    }

    private fun requestGetRepos() {
        networkState.value = NetworkState.Loading()

        viewModelScope.launch {
            val response = githubRepoRepository.getGithubRepoList()
            if (response.isSuccessful)
                networkState.value = NetworkState.Success(response.body().orEmpty())
            else
                networkState.value = NetworkState.Error(response.message(), null)
        }
    }
}