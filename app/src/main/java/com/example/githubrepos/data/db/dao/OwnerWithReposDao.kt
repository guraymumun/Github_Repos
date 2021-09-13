package com.example.githubrepos.data.db.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.example.githubrepos.data.db.entity.OwnerEntity
import com.example.githubrepos.data.db.entity.OwnerWithRepos
import com.example.githubrepos.data.db.entity.RepoEntity

@Dao
interface OwnerWithReposDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveRepos(repos: List<RepoEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveOwner(owner: OwnerEntity)

    @Transaction
    fun saveOwnerWithRepos(ownerWithRepos: List<OwnerWithRepos>) {
        ownerWithRepos.forEach {
            saveOwner(it.owner)
            saveRepos(it.repos)
        }
    }

    @Query("SELECT * FROM owner")
    fun loadAllRepos(): LiveData<List<OwnerWithRepos>>

    @Query("SELECT * FROM owner")
    fun loadReposWithPaging(): PagingSource<Int, OwnerWithRepos>

    @Query("DELETE FROM owner")
    suspend fun clear()

    @Query("SELECT COUNT(id) FROM repo")
    fun getRepoCount(): Int
}