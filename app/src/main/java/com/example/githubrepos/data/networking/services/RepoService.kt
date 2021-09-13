package com.example.githubrepos.data.networking.services

import com.example.githubrepos.domain.model.Repo
import retrofit2.http.GET
import retrofit2.http.Query

interface RepoService {

    @GET("orgs/square/repos")
    suspend fun getRepos(@Query("page") page: Int, @Query("per_page") perPage: Int): List<Repo>
}