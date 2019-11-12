package com.github.popular.ui.listing

import android.content.Context

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.github.popular.R
import com.github.popular.utils.exhaustive
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopularListingFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    private val viewModel: PopularListingViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_popular_listing, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.liveData.observe(this, Observer { state ->
            when (state) {
                PopularListingViewModel.States.Error -> Log.e("error", "generic error")
                is PopularListingViewModel.States.PopularItems -> Log.e("result", state.items.toString())
            }.exhaustive
        })
        viewModel.send(PopularListingViewModel.Events.OnAttach(PAGE_SIZE))
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
        fun onRepoClicked()
    }

    companion object {
        const val TAG = "popular_listing_fragment"
        private const val PAGE_SIZE = 20
        @JvmStatic
        fun newInstance() = PopularListingFragment()
    }
}
