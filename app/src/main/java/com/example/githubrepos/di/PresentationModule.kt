package com.example.githubrepos.di

import com.example.githubrepos.presentation.viewmodel.MainViewModel
import com.example.githubrepos.presentation.viewmodel.RepoDetailsViewModel
import com.example.githubrepos.presentation.viewmodel.RepoListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { MainViewModel() }
    viewModel { RepoListViewModel(get()) }
    viewModel { RepoDetailsViewModel() }
}