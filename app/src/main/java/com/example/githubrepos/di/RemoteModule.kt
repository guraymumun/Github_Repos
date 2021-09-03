package com.example.githubrepos.di

import com.example.githubrepos.data.networking.ServiceGenerator
import com.example.githubrepos.data.networking.services.RepoService
import org.koin.dsl.module
import retrofit2.Retrofit

val remoteModule = module {
    single { ServiceGenerator.getRetrofit() }
    factory { get<Retrofit>().create(RepoService::class.java) }
}