package com.github.popular.ui.listing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.popular.domain.usecase.PopularListingUseCase
import com.github.popular.domain.usecase.PopularReposPageRequest
import com.github.popular.utils.exhaustive
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import java.util.concurrent.TimeUnit

class PopularListingViewModel(
    private val popularListingUseCase: PopularListingUseCase,
    private val mainScheduler: Scheduler,
    private val networkScheduler: Scheduler
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val mutableLiveData: MutableLiveData<States> = MutableLiveData()
    val liveData: LiveData<States> get() = mutableLiveData
    fun send(event: Events) {
        when (event) {
            is Events.OnAttach -> onAttach(event.pageSize, event.refreshMillis)
        }.exhaustive
    }

    private fun onAttach(pageSize: Int, refreshMillis: MilliSeconds) {
        mutableLiveData.postValue(States.Loading)

        refreshPopularItems(pageSize, refreshMillis)
    }

    private fun refreshPopularItems(pageSize: Int, refreshMillis: MilliSeconds) {
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
            .repeatWhen { completed -> completed.delay(refreshMillis, TimeUnit.MILLISECONDS) }
            .retryWhen { failed -> failed.delay(refreshMillis, TimeUnit.MILLISECONDS) }
            .subscribe({ items ->
                mutableLiveData.postValue(States.PopularItems(items.map {
                    PopularRepoListItem(
                        id = it.id,
                        name = it.name,
                        owner = it.owner,
                        starsCount = it.starsCount,
                        ownerAvatar = it.ownerAvatar,
                        description = it.description
                    )
                }))
            }, {
                mutableLiveData.postValue(States.Error)
            })
            .addTo(compositeDisposable)
    }

    private fun disposeRequests() {
        compositeDisposable.clear()
    }

    override fun onCleared() {
        super.onCleared()
        disposeRequests()
    }

    sealed class Events {

        data class OnAttach(val pageSize: Int, val refreshMillis: MilliSeconds) : PopularListingViewModel.Events()
    }

    sealed class States {
        object Error : States()
        object Loading : States()
        data class PopularItems(val items: List<PopularRepoListItem>) : States()
    }

}
typealias MilliSeconds = Long