package com.example.githubrepos.presentation.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.example.githubrepos.R
import com.example.githubrepos.databinding.RepoDetailsFragmentBinding
import com.example.githubrepos.presentation.viewmodel.RepoDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepoDetailsFragment : BaseFragment<RepoDetailsFragmentBinding, RepoDetailsViewModel>(R.layout.repo_details_fragment) {

    private val viewModel: RepoDetailsViewModel by viewModel()
    private val args: RepoDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.repo = args.repo
    }

    override fun getVM(): RepoDetailsViewModel {
        return viewModel
    }

}