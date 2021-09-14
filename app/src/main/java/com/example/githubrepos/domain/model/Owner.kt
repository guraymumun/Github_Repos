package com.example.githubrepos.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Owner(val id: Int, @SerializedName("avatar_url") var avatar: String) : Parcelable
