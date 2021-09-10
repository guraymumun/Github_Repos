package com.example.githubrepos.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.githubrepos.data.db.RepoDtoToEntityMapper
import com.example.githubrepos.data.db.dao.OwnerWithReposDao
import com.example.githubrepos.data.networking.NetworkState
import com.example.githubrepos.data.networking.RepoEntityToDtoMapper
import com.example.githubrepos.data.networking.services.RepoService
import com.example.githubrepos.domain.model.Repo

interface GithubRepoRepository {
    suspend fun getGithubRepoList(): LiveData<NetworkState<List<Repo>>>
}

class GithubRepositoryImpl(
    private val repoService: RepoService,
    private val dao: OwnerWithReposDao,
    private val mapperDtoToEntity: RepoDtoToEntityMapper,
    private val mapperEntityToDto: RepoEntityToDtoMapper
) : GithubRepoRepository {

//    override suspend fun getGithubRepoList(): LiveData<NetworkState<List<Repo>>> {
//        try {
//            val responseList = repoService.getRepos()
//            dao.saveOwnerWithRepos(mapperDtoToEntity.map(responseList))
//            return NetworkState.Success(responseList)
//        } catch (httpException: HttpException) {
//            httpException.response()?.errorBody()?.let {
//                val errorBody = Gson().fromJson(it.string(), ErrorResponse::class.java)
//                return NetworkState.Error(errorBody.message, loadReposFromDB())
//            }
//        } catch (e: Exception) {
//            //handle other exceptions
//            //hasNetwork?
//            e.printStackTrace()
//            return NetworkState.Error("General Exception", loadReposFromDB())
//        }
//        return NetworkState.Error("General Error", loadReposFromDB())
//    }

    override suspend fun getGithubRepoList(): LiveData<NetworkState<List<Repo>>> {
        return object : NetworkBoundResource<List<Repo>>() {
            override fun saveCallResult(item: List<Repo>) {
                dao.saveOwnerWithRepos(mapperDtoToEntity.map(item))
            }

            override fun loadFromDb(): LiveData<List<Repo>> = Transformations.map(dao.loadAllRepos()) {
                mapperEntityToDto.map(it)
            }

            override suspend fun createCall() = repoService.getRepos()

            override fun onFetchFailed() {

            }
        }.build().asLiveData()
    }
}