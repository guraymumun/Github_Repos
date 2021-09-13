package com.example.githubrepos.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.githubrepos.data.db.RepoDatabase
import com.example.githubrepos.data.db.RepoDtoToEntityMapper
import com.example.githubrepos.data.db.entity.OwnerWithRepos
import com.example.githubrepos.data.networking.services.RepoService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

const val GITHUB_STARTING_PAGE_INDEX = 1

@ExperimentalPagingApi
class RepoRemoteMediator(
    private val repoService: RepoService,
    private val db: RepoDatabase,
    private val mapper: RepoDtoToEntityMapper
) : RemoteMediator<Int, OwnerWithRepos>() {

    override suspend fun initialize(): InitializeAction {
        // Require that remote REFRESH is launched on initial load and succeeds before launching
        // remote PREPEND / APPEND.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, OwnerWithRepos>
    ): MediatorResult {

        val repoCount = withContext(Dispatchers.IO) {
            db.repoDao().getRepoCount()
        }

        val page = when (loadType) {
            LoadType.REFRESH -> {
                GITHUB_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(true)
            }
            LoadType.APPEND -> {
                repoCount.div(RepoPagingSource.NETWORK_PAGE_SIZE).plus(1)
            }
        }

        Log.e("PAGE VALUE", "PAGE VALUE: " + page + ", STATUS: " + loadType.name)

        return try {
            val response = repoService.getRepos(page, RepoPagingSource.NETWORK_PAGE_SIZE)
            val endOfPaginationReached = response.size != RepoPagingSource.NETWORK_PAGE_SIZE

            db.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    db.repoDao().clear()
                }
                db.repoDao().saveOwnerWithRepos(mapper.map(response))
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

//    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Repo>): RemoteKeysEntity? {
//        // Get the last page that was retrieved, that contained items.
//        // From that last page, get the last item
//        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
//            ?.let { repo ->
//                // Get the remote keys of the last item retrieved
//                db.remoteKeysDao().remoteKeysRepoId(repo.id)
//            }
//    }
//
//    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Repo>): RemoteKeysEntity? {
//        // Get the first page that was retrieved, that contained items.
//        // From that first page, get the first item
//        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
//            ?.let { repo ->
//                // Get the remote keys of the first items retrieved
//                db.remoteKeysDao().remoteKeysRepoId(repo.id)
//            }
//    }
//
//    private suspend fun getRemoteKeyClosestToCurrentPosition(
//        state: PagingState<Int, OwnerWithRepos>
//    ): RemoteKeysEntity? {
//        // The paging library is trying to load data after the anchor position
//        // Get the item closest to the anchor position
//        return state.anchorPosition?.let { position ->
//            state.closestItemToPosition(position)?.id?.let { repoId ->
//                db.remoteKeysDao().remoteKeysRepoId(repoId)
//            }
//        }
//    }
}