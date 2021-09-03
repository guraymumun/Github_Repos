package com.example.githubrepos.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepos.data.networking.ServiceGenerator
import com.example.githubrepos.data.networking.services.RepoService
import com.example.githubrepos.data.repositories.GithubRepoRepository
import com.example.githubrepos.domain.model.Repo
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val githubRepoRepository: GithubRepoRepository) : ViewModel() {
    private var _list = MutableLiveData<ArrayList<Repo>>(arrayListOf())
    val list: LiveData<ArrayList<Repo>> get() = _list

    init {
        requestGetRepos()
    }

    private fun requestGetRepos() {
        viewModelScope.launch {
            _list.value = githubRepoRepository.getGithubRepoList()
        }
    }
}