package com.example.githubrepos.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepos.data.networking.NetworkState
import com.example.githubrepos.data.repositories.GithubRepoRepository
import com.example.githubrepos.domain.model.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val githubRepoRepository: GithubRepoRepository) : ViewModel() {

    private var _networkState = MediatorLiveData<NetworkState<List<Repo>>>()
    val networkState: LiveData<NetworkState<List<Repo>>> get() = _networkState

    init {
        requestGetRepos()
    }

    private fun requestGetRepos() {
//        _networkState.value = NetworkState.Loading

//        viewModelScope.launch(Dispatchers.IO) {
//            _networkState.postValue(githubRepoRepository.getGithubRepoList())
//        }

        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                githubRepoRepository.getGithubRepoList()
            }
            _networkState.addSource(result) {
                _networkState.postValue(it)
            }
        }
    }
}