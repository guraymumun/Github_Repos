package com.example.githubrepos.data.db

interface BaseMapper<In, Out>{
    fun map(model: In): Out
}