package com.example.githubrepos.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "owner")
data class OwnerEntity(
    @PrimaryKey val id: Int,
    var avatar: String?
)