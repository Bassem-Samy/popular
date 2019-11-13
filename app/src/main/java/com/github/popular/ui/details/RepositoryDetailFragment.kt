package com.github.popular.ui.details


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer

import com.github.popular.R
import com.github.popular.utils.ImageLoader
import com.github.popular.utils.MilliSeconds
import com.github.popular.utils.exhaustive
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepositoryDetailFragment : Fragment() {
    private var repositoryId: String? = null
    private val viewModel: RepositoryDetailsViewModel by viewModel()
    private val imageLoader: ImageLoader by inject()
    private lateinit var detailView: View
    private lateinit var nameText: AppCompatTextView
    private lateinit var ownerText: AppCompatTextView
    private lateinit var starsCountText: AppCompatTextView
    private lateinit var forksCountText: AppCompatTextView
    private lateinit var openIssuesText: AppCompatTextView
    private lateinit var languageText: AppCompatTextView
    private lateinit var languagePrefixText: AppCompatTextView
    private lateinit var descriptionText: AppCompatTextView
    private lateinit var detailImage: AppCompatImageView
    private lateinit var detailProgress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            repositoryId = it.getString(ARG_REPOSITORY_ID, null)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_repository_detail, container, false)
        detailView = view.findViewById(R.id.detail_container)
        nameText = view.findViewById(R.id.detail_name)
        ownerText = view.findViewById(R.id.detail_owner_name)
        starsCountText = view.findViewById(R.id.detail_stars_count)
        forksCountText = view.findViewById(R.id.detail_forks_count)
        openIssuesText = view.findViewById(R.id.detail_issues_count)
        languageText = view.findViewById(R.id.detail_language)
        languagePrefixText = view.findViewById(R.id.detail_language_prefix)
        descriptionText = view.findViewById(R.id.detail_description)
        detailImage = view.findViewById(R.id.detail_image)
        detailProgress = view.findViewById(R.id.detail_progress)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.liveData.observe(this, Observer { state ->
            when (state) {
                RepositoryDetailsViewModel.States.Error -> showError()
                RepositoryDetailsViewModel.States.Loading -> showLoading()
                is RepositoryDetailsViewModel.States.Details -> displayItem(state.repository)
            }.exhaustive
        })
        repositoryId?.let {
            viewModel.send(RepositoryDetailsViewModel.Events.OnAttach(repoId = it, updateInterval = UPDATE_INTERVAL))
        }

    }

    private fun displayItem(repository: RepositoryDetail) {
        detailProgress.visibility = View.GONE
        detailView.visibility = View.VISIBLE
        nameText.text = repository.name
        ownerText.text = repository.owner
        starsCountText.text = repository.starsCount.toString()
        forksCountText.text = repository.forksCount.toString()
        openIssuesText.text = repository.openIssuesCount.toString()

        repository.language.fold({
            languageText.visibility = View.GONE
            languagePrefixText.visibility = View.GONE
        }, {
            languageText.apply {
                visibility = View.VISIBLE
                text = it
            }
            languagePrefixText.visibility = View.VISIBLE
        })
        repository.description.fold({}, { descriptionText.text = it })
        imageLoader.loadImage(detailImage, repository.ownerAvatar, R.drawable.ic_launcher_background)

    }

    private fun showLoading() {
        detailProgress.visibility = View.VISIBLE
        detailView.visibility = View.GONE
    }

    private fun showError() {
        detailProgress.visibility = View.GONE
        detailView.visibility = View.GONE
    }

    companion object {
        private const val ARG_REPOSITORY_ID = "repository_id"
        const val TAG = "repository_detail_fragment"
        private const val UPDATE_INTERVAL: MilliSeconds = 1000
        @JvmStatic
        fun newInstance(repositoryId: String) =
            RepositoryDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_REPOSITORY_ID, repositoryId)
                }
            }
    }
}
