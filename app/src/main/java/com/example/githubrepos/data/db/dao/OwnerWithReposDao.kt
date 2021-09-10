package com.example.githubrepos.data.db.dao

import androidx.lifecycle.LiveData
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

    @Transaction
    @Query("SELECT * FROM owner")
    fun loadAllRepos(): LiveData<List<OwnerWithRepos>>
}