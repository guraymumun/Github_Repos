package com.example.githubrepos.domain.model

data class Repo(
    val id: Int,
    var name: String,
    var description: String?,
    var owner: Owner
)
