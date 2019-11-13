package com.github.popular.ui.listing

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.popular.R
import com.github.popular.utils.MilliSeconds
import com.github.popular.utils.exhaustive
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopularListingFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    private val viewModel: PopularListingViewModel by viewModel()
    private val popularItemsAdapter = PopularRepoAdapter(onItemClicked = ::onPopularItemClicked, imageLoader = get())

    private lateinit var recylerView: RecyclerView
    private lateinit var mainProgress: ProgressBar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_popular_listing, container, false)
        mainProgress = view.findViewById(R.id.main_progress)
        recylerView = view.findViewById(R.id.popular_items_recycler_view)
        recylerView.apply {
            adapter = popularItemsAdapter
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.liveData.observe(this, Observer { state ->
            when (state) {
                PopularListingViewModel.States.Error -> showError()
                PopularListingViewModel.States.Loading -> showLoading()
                is PopularListingViewModel.States.PopularItems -> displayPopularItems(state.items)
            }.exhaustive
        })
        viewModel.send(PopularListingViewModel.Events.OnAttach(PAGE_SIZE, REFRESH_MILLIS))
    }

    private fun displayPopularItems(items: List<PopularRepoListItem>) {
        mainProgress.visibility = View.GONE
        popularItemsAdapter.setItems(items)
    }

    private fun showLoading() {
        mainProgress.visibility = View.VISIBLE
    }

    private fun showError() {
        mainProgress.visibility = View.GONE
        Toast.makeText(context, R.string.general_error, Toast.LENGTH_SHORT).show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onRepoClicked(repositoryId: String)
    }

    private fun onPopularItemClicked(popularRepoListItem: PopularRepoListItem) {
        listener?.onRepoClicked(popularRepoListItem.id)
    }

    companion object {
        const val TAG = "popular_listing_fragment"
        private const val PAGE_SIZE = 20
        private const val REFRESH_MILLIS: MilliSeconds = 5000
        @JvmStatic
        fun newInstance() = PopularListingFragment()
    }
}


