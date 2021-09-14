package com.example.githubrepos.presentation.ui.fragment

import android.os.Bundle
import android.view.View
import com.example.githubrepos.R
import com.example.githubrepos.databinding.RepoDetailsFragmentBinding
import com.example.githubrepos.domain.model.Repo
import com.example.githubrepos.presentation.viewmodel.RepoDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepoDetailsFragment : BaseFragment<RepoDetailsFragmentBinding, RepoDetailsViewModel>(R.layout.repo_details_fragment) {

    private val viewModel: RepoDetailsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.repo = arguments?.get("repo") as Repo
    }

    override fun getVM(): RepoDetailsViewModel {
        return viewModel
    }

}