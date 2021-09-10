package com.example.githubrepos.presentation.viewmodel

import androidx.lifecycle.*
import com.example.githubrepos.data.networking.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel : ViewModel() {

    var showLoading = MutableLiveData<Boolean>()

    protected fun <T> makeRequest(mediator: MediatorLiveData<NetworkState<T>>, request: suspend () -> LiveData<NetworkState<T>>,
                                  response: (NetworkState<T>) -> Unit) {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = request()
                mediator.addSource(result) {
                    showLoading.postValue(it is NetworkState.Loading)
                    response(it)
                }
            }
        }
    }
}