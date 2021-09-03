package com.example.githubrepos.di

import com.example.githubrepos.data.networking.ServiceGenerator
import com.example.githubrepos.data.networking.services.RepoService
import com.example.githubrepos.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val presentationModule = module {
    viewModel { MainViewModel(get()) }
}