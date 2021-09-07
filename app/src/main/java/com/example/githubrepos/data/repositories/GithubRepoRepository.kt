package com.example.githubrepos.data.repositories

import com.example.githubrepos.data.networking.services.RepoService
import com.example.githubrepos.domain.model.Repo
import retrofit2.Response

interface GithubRepoRepository {
    suspend fun getGithubRepoList(): Response<List<Repo>>
}

class GithubRepositoryImpl(private val repoService: RepoService) : GithubRepoRepository {
    override suspend fun getGithubRepoList() = repoService.getRepos()
}