package com.example.githubrepos.data.networking.helper

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class ResultAdapter(
    private val type: Type
): CallAdapter<Type, Call<NetworkState<Type>>> {
    override fun responseType() = type
    override fun adapt(call: Call<Type>): Call<NetworkState<Type>> = ResultCall(call)
}