package com.example.githubrepos.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.githubrepos.data.networking.NetworkState
import com.example.githubrepos.data.repositories.GithubRepoRepository
import com.example.githubrepos.domain.model.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(private val githubRepoRepository: GithubRepoRepository) : BaseViewModel() {

    private var _repoLiveDataList = MediatorLiveData<NetworkState<List<Repo>>>()
    val repoLiveDataList: LiveData<NetworkState<List<Repo>>> get() = _repoLiveDataList

    init {
//        requestGetRepos()
//        requestGetReposWithPaging()
    }

    private fun requestGetRepos() {
        makeRequest (
            mediator = _repoLiveDataList,
            request = { githubRepoRepository.getGithubRepoList() },
            response = { _repoLiveDataList.postValue(it) }
        )
    }

    fun requestGetReposWithPaging(): Flow<PagingData<Repo>> {
        return githubRepoRepository.getGithubRepoListWithPaging().cachedIn(viewModelScope)
    }

    fun insertRandomRepoToDB() {
        viewModelScope.launch(Dispatchers.IO) {
            githubRepoRepository.insertRepoToDb()
        }
    }
}