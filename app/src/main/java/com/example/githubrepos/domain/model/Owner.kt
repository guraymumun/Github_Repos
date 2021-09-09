package com.example.githubrepos.domain.model

import com.google.gson.annotations.SerializedName

data class Owner(val id: Int, @SerializedName("avatar_url") var avatar: String)
