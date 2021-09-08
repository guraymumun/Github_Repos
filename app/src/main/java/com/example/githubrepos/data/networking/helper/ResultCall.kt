package com.example.githubrepos.data.networking.helper

import com.example.githubrepos.domain.model.ErrorResponse
import com.google.gson.Gson
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ResultCall<T>(proxy: Call<T>) : CallDelegate<T, NetworkState<T>>(proxy) {

    override fun enqueueImpl(callback: Callback<NetworkState<T>>) = proxy.enqueue(object: Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            val code = response.code()
            val result = if (code in 200 until 300) {
                val body = response.body()
                val successResult: NetworkState.Success<T?> = NetworkState.Success(body)
                successResult
            } else {
                response.errorBody()?.let {
                    val errorBody = Gson().fromJson(it.string(), ErrorResponse::class.java)
                    NetworkState.Error(errorBody.message)
                }
            }

            callback.onResponse(this@ResultCall, Response.success(result as NetworkState<T>?))
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            val result = if (t is IOException) {
                NetworkState.Error<T>(t.message.orEmpty())
            } else {
                NetworkState.Error("General Error!")
            }

            callback.onResponse(this@ResultCall, Response.success(result))
        }
    })

    override fun cloneImpl() = ResultCall(proxy.clone())
    override fun timeout(): Timeout {
        TODO("Not yet implemented")
    }
}