package com.example.githubrepos.domain.model

import java.io.Serializable

data class Repo(
    val id: Int,
    var name: String,
    var description: String?,
    var owner: Owner
) : Serializable
