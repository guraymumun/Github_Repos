package com.example.githubrepos.data.networking

import com.example.githubrepos.BuildConfig
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val CLIENT_SECRET = "66daa6c1695a0b302b3da090f7412828fb08eeb6"
private const val CLIENT_ID = "6db8db1802b2f2995a23"

class ServiceGenerator {

    companion object {

        fun getRetrofit(): Retrofit {
            val httpClient = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                httpClient.addInterceptor(loggingInterceptor)
            }
            val interceptor = Interceptor { chain: Interceptor.Chain ->
                val original: Request = chain.request()
                val request: Request = original.newBuilder()
                    .header("Authorization", Credentials.basic(CLIENT_ID, CLIENT_SECRET))
                    .build()
                chain.proceed(request)
            }
            httpClient.addInterceptor(interceptor)

            return Retrofit.Builder()
                .client(httpClient.build())
                .baseUrl(BuildConfig.BASE_URL)
//                .addCallAdapterFactory(MyCallAdapterFactory())
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