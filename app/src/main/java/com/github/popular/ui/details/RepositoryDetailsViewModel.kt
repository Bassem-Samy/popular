package com.github.popular.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.popular.domain.usecase.GetRepositoryUseCase
import com.github.popular.utils.MilliSeconds
import com.github.popular.utils.exhaustive
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import java.util.concurrent.TimeUnit

class RepositoryDetailsViewModel(
    private val detailsUseCase: GetRepositoryUseCase,
    private val mainScheduler: Scheduler,
    private val networkScheduler: Scheduler
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val mutableLiveData: MutableLiveData<States> = MutableLiveData()
    val liveData: LiveData<States> get() = mutableLiveData

    fun send(event: Events) {
        when (event) {
            is Events.OnAttach -> onAttach(event.repoId, event.updateInterval)
        }.exhaustive
    }

    private fun onAttach(repoId: String, updateInterval: MilliSeconds) {
        mutableLiveData.postValue(States.Loading)

        var floable =
            detailsUseCase.getRepositoryById(id = repoId)
                .observeOn(mainScheduler)
                .subscribeOn(networkScheduler)
                .toFlowable()

        if (updateInterval > 0) {
            floable = floable.repeatWhen { completed -> completed.delay(updateInterval, TimeUnit.MILLISECONDS) }
                .retryWhen { failed -> failed.delay(updateInterval, TimeUnit.MILLISECONDS) }
        }
        floable.subscribe({
            mutableLiveData.postValue(
                States.Details(
                    repository =
                    RepositoryDetail(
                        id = it.id,
                        name = it.name,
                        owner = it.owner,
                        starsCount = it.starsCount,
                        forksCount = it.forksCount,
                        openIssuesCount = it.openIssuesCount,
                        ownerAvatar = it.ownerAvatar,
                        language = it.language,
                        description = it.description
                    )
                )
            )
        }, {
            mutableLiveData.postValue(States.Error)
        }).addTo(compositeDisposable)
    }

    private fun disposeRequests() {
        compositeDisposable.clear()
    }

    override fun onCleared() {
        super.onCleared()
        disposeRequests()
    }

    sealed class Events {
        data class OnAttach(val repoId: String, val updateInterval: MilliSeconds) : Events()
    }

    sealed class States {
        object Error : States()
        object Loading : States()
        data class Details(val repository: RepositoryDetail) : States()
    }
}