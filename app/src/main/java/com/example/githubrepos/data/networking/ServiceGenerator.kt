package com.example.githubrepos.data.networking

import com.example.githubrepos.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class ServiceGenerator {

    companion object {

        fun getRetrofit(): Retrofit {
            val httpClient = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                httpClient.addInterceptor(loggingInterceptor)
            }

            return Retrofit.Builder()
                .client(httpClient.build())
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create()).build()
        }

        // all of this goes to RemoteModule.kt
//        fun <S> createService(serviceClass: Class<S>): S {
//
//            if (BuildConfig.DEBUG) {
//                val loggingInterceptor = HttpLoggingInterceptor()
//                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
//                httpClient.addInterceptor(loggingInterceptor)
//            }
//            builder.client(httpClient.build())
//            retrofit = builder.build()
//            return retrofit.create(serviceClass)
//        }
    }
}