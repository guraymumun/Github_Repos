package com.example.githubrepos.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubrepos.data.db.dao.OwnerWithReposDao
import com.example.githubrepos.data.db.entity.OwnerEntity
import com.example.githubrepos.data.db.entity.RepoEntity

@Database(entities = [RepoEntity::class, OwnerEntity::class], version = 5)
abstract class RepoDatabase : RoomDatabase() {
    abstract fun repoDao(): OwnerWithReposDao
}