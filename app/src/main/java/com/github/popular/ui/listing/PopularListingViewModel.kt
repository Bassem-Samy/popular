package com.github.popular.ui.listing

import android.util.Log
import androidx.lifecycle.ViewModel
import com.github.popular.domain.usecase.PopularListingUseCase
import com.github.popular.domain.usecase.PopularReposPageRequest
import com.github.popular.utils.exhaustive
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class PopularListingViewModel(
    private val popularListingUseCase: PopularListingUseCase,
    private val mainScheduler: Scheduler,
    private val networkScheduler: Scheduler
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    fun send(event: Events) {
        when (event) {
            is Events.OnAttach -> onAttach(event.pageSize)
        }.exhaustive
    }

    private fun onAttach(pageSize: Int) {
        disposeRequests()
        popularListingUseCase.getPopularRepos(
            PopularReposPageRequest(
                keyword = "Android",
                page = 1,
                pageSize = pageSize
            )
        )
            .subscribeOn(networkScheduler)
            .observeOn(mainScheduler)
            .subscribe({ items ->
                Log.e("result", items.toString())
            }, { throwable ->
                Log.e("result", throwable.message)
            })
            .addTo(compositeDisposable)
    }

    private fun disposeRequests() {
        compositeDisposable.clear()
    }

    sealed class Events {
        data class OnAttach(val pageSize: Int) : PopularListingViewModel.Events()
    }

    sealed class Messages {}
}