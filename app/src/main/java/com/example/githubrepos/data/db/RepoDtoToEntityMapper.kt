package com.example.githubrepos.data.db

import com.example.githubrepos.data.db.entity.OwnerEntity
import com.example.githubrepos.data.db.entity.OwnerWithRepos
import com.example.githubrepos.data.db.entity.RepoEntity
import com.example.githubrepos.domain.model.Repo

class RepoDtoToEntityMapper : BaseMapper<List<Repo>, List<OwnerWithRepos>> {

    override fun map(model: List<Repo>): List<OwnerWithRepos> = mutableListOf<OwnerWithRepos>().apply {
        model.forEach {
//            this.add(OwnerWithRepos(it.owner, listOf()))
//            this.add(RepoEntity(it.id, it.name, it.description, it.owner.id))
            this.add(OwnerWithRepos(OwnerEntity(it.owner.id, it.owner.avatar), listOf(RepoEntity(it.id, it.name, it.description, it.owner.id))))
        }
    }


}