package com.example.githubrepos.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.*
import com.example.githubrepos.data.RepoPagingSource.Companion.NETWORK_PAGE_SIZE
import com.example.githubrepos.data.RepoRemoteMediator
import com.example.githubrepos.data.db.RepoDatabase
import com.example.githubrepos.data.db.RepoDtoToEntityMapper
import com.example.githubrepos.data.db.entity.OwnerEntity
import com.example.githubrepos.data.db.entity.OwnerWithRepos
import com.example.githubrepos.data.db.entity.RepoEntity
import com.example.githubrepos.data.networking.NetworkState
import com.example.githubrepos.data.networking.RepoEntityToDtoMapper
import com.example.githubrepos.data.networking.services.RepoService
import com.example.githubrepos.domain.model.Repo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface GithubRepoRepository {
    suspend fun getGithubRepoList(): LiveData<NetworkState<List<Repo>>>
    suspend fun insertRepoToDb()
    fun getGithubRepoListWithPaging(): Flow<PagingData<Repo>>
}

class GithubRepositoryImpl(
    private val repoService: RepoService,
    private val db: RepoDatabase,
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

    @ExperimentalPagingApi
    override fun getGithubRepoListWithPaging(): Flow<PagingData<Repo>> {
        val liveDataResult = Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
//                enablePlaceholders = false,
//                prefetchDistance = PREFETCH_DISTANCE,
            ),
            remoteMediator = RepoRemoteMediator(repoService, db, mapperDtoToEntity),
//            pagingSourceFactory = {
//                db.repoDao().loadReposWithPaging()
//            }
        ) { db.repoDao().loadReposWithPaging() }.flow

        return liveDataResult.map { pagingData ->
            pagingData.flatMap { ownerWithRepos ->
                mapperEntityToDto.map(listOf(ownerWithRepos))
            }
        }

//        return Transformations.map(liveDataResult) { pagingData ->
//            pagingData.flatMap { ownerWithRepos ->
//                mapperEntityToDto.map(listOf(ownerWithRepos))
//            }
//        }
    }

    override suspend fun getGithubRepoList(): LiveData<NetworkState<List<Repo>>> {
        return object : NetworkBoundResource<List<Repo>>() {
            override fun saveCallResult(item: List<Repo>) {
                db.repoDao().saveOwnerWithRepos(mapperDtoToEntity.map(item))
            }

            override fun loadFromDb(): LiveData<List<Repo>> =
                Transformations.map(db.repoDao().loadAllRepos()) {
                    mapperEntityToDto.map(it)
                }

            override suspend fun createCall() = repoService.getRepos(1, 10)

            override fun onFetchFailed() {

            }
        }.build().asLiveData()
    }

    override suspend fun insertRepoToDb() {
        val randomId = (100..200).random()
        db.repoDao().saveOwnerWithRepos(
            listOf(
                OwnerWithRepos(
                    OwnerEntity(randomId, "https://upload.wikimedia.org/wikipedia/commons/7/74/Kotlin_Icon.png"),
                    listOf(RepoEntity(randomId, "RandomRepo_$randomId", null, randomId))
                )
            )
        )
    }
}