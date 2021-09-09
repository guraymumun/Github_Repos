package com.example.githubrepos.data.networking.services

import com.example.githubrepos.domain.model.Repo
import retrofit2.http.GET

interface RepoService {

    @GET("orgs/square/repos")
    suspend fun getRepos(): List<Repo>
}