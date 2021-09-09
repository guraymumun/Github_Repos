package com.example.githubrepos.data.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class OwnerWithRepos(
    @Embedded val owner: OwnerEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "ownerId"
    )
    val repos: List<RepoEntity>
)