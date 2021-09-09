package com.example.githubrepos.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repo")
data class RepoEntity(
    @PrimaryKey val id: Int,
    var name: String,
    var description: String?,
    val ownerId: Int
)