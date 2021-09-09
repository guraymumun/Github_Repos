package com.example.githubrepos.di

import com.example.githubrepos.data.repositories.GithubRepoRepository
import com.example.githubrepos.data.repositories.GithubRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory<GithubRepoRepository> { GithubRepositoryImpl(get(), get(), get(), get()) }
}