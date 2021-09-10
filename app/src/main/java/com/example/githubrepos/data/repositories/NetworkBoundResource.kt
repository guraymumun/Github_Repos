/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.githubrepos.data.repositories

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.githubrepos.data.networking.NetworkState
import com.example.githubrepos.domain.model.ErrorResponse
import com.google.gson.Gson
import kotlinx.coroutines.*
import retrofit2.HttpException
import kotlin.coroutines.coroutineContext

abstract class NetworkBoundResource<ResultType> {

    private val result = MediatorLiveData<NetworkState<ResultType>>()

    suspend fun build(): NetworkBoundResource<ResultType> {
        result.postValue(NetworkState.Loading)
        CoroutineScope(coroutineContext).launch(SupervisorJob()) {
            fetchFromNetwork()
        }
        return this
    }

    @MainThread
    private fun setValue(newValue: NetworkState<ResultType>) {
        if (result.value != newValue) {
            result.postValue(newValue)
        }
    }

    private suspend fun setError(message: String) {
        withContext(Dispatchers.Main) {
            result.addSource(loadFromDb()) { cachedData ->
                setValue(NetworkState.Error(message, cachedData))
            }
        }
    }

    private suspend fun fetchFromNetwork() {
        try {
            val apiResponse = createCall()
            saveCallResult(apiResponse)
            setValue(NetworkState.Success(apiResponse))
        } catch (httpException: HttpException) {
            httpException.response()?.errorBody()?.let {
                val errorBody = Gson().fromJson(it.string(), ErrorResponse::class.java)
                setError(errorBody.message)
                onFetchFailed()
            }
        } catch (e: Exception) {
            //handle other exceptions
            //hasNetwork?
            e.printStackTrace()
            setError("General Exception")
            onFetchFailed()
        }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<NetworkState<ResultType>>

    @WorkerThread
    protected abstract fun saveCallResult(item: ResultType)

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract suspend fun createCall(): ResultType
}
