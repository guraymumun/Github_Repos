package com.example.githubrepos.data.repositories

import com.example.githubrepos.data.networking.services.RepoService
import com.example.githubrepos.domain.model.Repo

interface GithubRepoRepository {
    suspend fun getGithubRepoList(): ArrayList<Repo>
}

class GithubRepositoryImpl(private val repoService: RepoService) : GithubRepoRepository {
    override suspend fun getGithubRepoList() = repoService.getRepos()
}