package com.example.githubrepos.data.repositories

import com.example.githubrepos.data.networking.helper.NetworkState
import com.example.githubrepos.data.networking.services.RepoService
import com.example.githubrepos.domain.model.ErrorResponse
import com.example.githubrepos.domain.model.Repo
import com.google.gson.Gson
import retrofit2.HttpException

interface GithubRepoRepository {
    suspend fun getGithubRepoList(): NetworkState<List<Repo>>
}

class GithubRepositoryImpl(private val repoService: RepoService) : GithubRepoRepository {
    override suspend fun getGithubRepoList(): NetworkState<List<Repo>> {
        try {
            return repoService.getRepos()
        } catch (httpException: HttpException) {
            httpException.response()?.errorBody()?.let {
                val errorBody = Gson().fromJson(it.string(), ErrorResponse::class.java)
                return NetworkState.Error(errorBody.message)
            }
        } catch (e: Exception) {
            //handle other exceptions
            //hasNetwork?
            e.printStackTrace()
            return NetworkState.Error("General Exception")
        }
        return NetworkState.Error("General Error")
    }
}