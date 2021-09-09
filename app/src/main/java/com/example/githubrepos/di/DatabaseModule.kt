package com.example.githubrepos.di

import androidx.room.Room
import com.example.githubrepos.data.db.RepoDatabase
import com.example.githubrepos.data.db.RepoDtoToEntityMapper
import com.example.githubrepos.data.networking.RepoEntityToDtoMapper
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room.databaseBuilder(androidApplication(), RepoDatabase::class.java, "Repo")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<RepoDatabase>().repoDao() }
    factory { RepoDtoToEntityMapper() }
    factory { RepoEntityToDtoMapper() }
}