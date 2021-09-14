package com.example.githubrepos.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Repo(
    val id: Int,
    var name: String,
    var description: String?,
    var owner: Owner
) : Parcelable
