package com.github.popular

import com.github.popular.domain.usecase.PopularListingUseCase
import com.github.popular.domain.usecase.PopularListingUseCaseImpl
import com.github.popular.repository.ReposRepository
import com.github.popular.repository.ReposRepositoryImpl
import com.github.popular.repository.RepositoryDomainMapper
import com.github.popular.ui.listing.PopularListingViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { RepositoryDomainMapper() }
    single<ReposRepository> {
        ReposRepositoryImpl(
            repositoriesApi = get(),
            repositoryDomainMapper = get()
        )
    }
    single<PopularListingUseCase> {
        PopularListingUseCaseImpl(reposRepository = get())
    }
    viewModel {
        PopularListingViewModel(
            popularListingUseCase = get(),
            mainScheduler = AndroidSchedulers.mainThread(),
            networkScheduler = Schedulers.io()
        )
    }
}