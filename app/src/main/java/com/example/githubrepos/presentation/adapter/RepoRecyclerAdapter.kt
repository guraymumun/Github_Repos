package com.example.githubrepos.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubrepos.databinding.ItemRepoBinding
import com.example.githubrepos.domain.model.Repo

class RepoRecyclerAdapter : PagingDataAdapter<Repo, RepoRecyclerAdapter.RepoViewHolder>(RepoDiffCallback()) {

    private lateinit var onItemClickListener: OnItemClickListener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RepoRecyclerAdapter.RepoViewHolder {
        val binding = ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoRecyclerAdapter.RepoViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class RepoViewHolder(private val binding: ItemRepoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: Repo) {
            binding.repo = repo
            binding.root.setOnClickListener {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(repo)
            }
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }
}

interface OnItemClickListener {
    fun onItemClick(repo: Repo)
}

class RepoDiffCallback : DiffUtil.ItemCallback<Repo>() {

    override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
        return oldItem == newItem
    }
}