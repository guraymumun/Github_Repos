package com.example.githubrepos.data.networking

sealed class NetworkState<out T> {
    data class Success<T>(val data: T) : NetworkState<T>()
    data class Error<T>(val message: String, val cachedData: T) : NetworkState<T>()
    object Loading : NetworkState<Nothing>()
}
