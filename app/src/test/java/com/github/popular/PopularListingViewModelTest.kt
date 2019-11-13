package com.github.popular

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.github.popular.domain.usecase.PopularListingUseCase
import com.github.popular.domain.usecase.PopularReposPageRequest
import com.github.popular.ui.listing.PopularListingViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`

class PopularListingViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mockUseCase: PopularListingUseCase = mock()
    val sut = PopularListingViewModel(
        popularListingUseCase = mockUseCase,
        mainScheduler = Schedulers.trampoline(),
        networkScheduler = Schedulers.trampoline()
    )
    private val mainRequest = PopularReposPageRequest(keyword = "Android", page = 1, pageSize = 100)

    @Test
    fun `calls get repos on attach`() {
        `when`(mockUseCase.getPopularRepos(popularReposPageRequest = mainRequest)).thenReturn(Single.just(emptyList()))
        sut.send(PopularListingViewModel.Events.OnAttach(pageSize = 100, refreshMillis = 0))
        verify(mockUseCase, times(1)).getPopularRepos(mainRequest)
    }

    @Test
    fun `shows loading when on attach`() {
        `when`(mockUseCase.getPopularRepos(popularReposPageRequest = mainRequest)).thenReturn(Single.just(emptyList()))
        val mockObserver: Observer<PopularListingViewModel.States> = mock()
        sut.liveData.observeForever(mockObserver)
        sut.send(PopularListingViewModel.Events.OnAttach(pageSize = 100, refreshMillis = 0))
        verify(mockObserver).onChanged(PopularListingViewModel.States.Loading)
    }

    @Test
    fun `shows error when Error`() {
        `when`(mockUseCase.getPopularRepos(popularReposPageRequest = mainRequest)).thenReturn(Single.error(Throwable("error message")))
        val mockObserver: Observer<PopularListingViewModel.States> = mock()
        sut.liveData.observeForever(mockObserver)
        sut.send(PopularListingViewModel.Events.OnAttach(pageSize = 100, refreshMillis = 0))
        verify(mockObserver).onChanged(PopularListingViewModel.States.Error)
    }

    @Test
    fun `displays correct ui data`() {
        `when`(mockUseCase.getPopularRepos(popularReposPageRequest = mainRequest)).thenReturn(Single.just(PopularItemsProvider.provideDomainRepos()))
        val mockObserver: Observer<PopularListingViewModel.States> = mock()
        sut.liveData.observeForever(mockObserver)
        sut.send(PopularListingViewModel.Events.OnAttach(pageSize = 100, refreshMillis = 0))
        verify(mockObserver).onChanged(PopularListingViewModel.States.PopularItems(PopularItemsProvider.providePopularRepoListItems()))
    }
}