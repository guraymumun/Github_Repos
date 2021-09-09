package com.example.githubrepos.data.networking

import com.example.githubrepos.data.db.BaseMapper
import com.example.githubrepos.data.db.entity.OwnerWithRepos
import com.example.githubrepos.domain.model.Owner
import com.example.githubrepos.domain.model.Repo

class RepoEntityToDtoMapper : BaseMapper<List<OwnerWithRepos>, List<Repo>> {

    override fun map(model: List<OwnerWithRepos>): List<Repo> = mutableListOf<Repo>().apply {
//        model.flatMap { it.repos }.forEach {
//            this.add(Repo(it.id, it.name, it.description, mode))
//        }
        model.forEach { owner ->
            owner.repos.forEach { repo ->
                this.add(Repo(repo.id, repo.name, repo.description, Owner(owner.owner.id, owner.owner.avatar.orEmpty())))
            }
        }
    }
}