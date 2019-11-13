package com.github.popular

import com.github.popular.domain.usecase.GetRepositoryUseCase
import com.github.popular.domain.usecase.GetRepositoryUseCaseImpl
import com.github.popular.domain.usecase.PopularListingUseCase
import com.github.popular.domain.usecase.PopularListingUseCaseImpl
import com.github.popular.repository.ReposRepository
import com.github.popular.repository.ReposRepositoryImpl
import com.github.popular.repository.RepositoryDomainMapper
import com.github.popular.ui.details.RepositoryDetailsViewModel
import com.github.popular.ui.listing.PopularListingViewModel
import com.github.popular.utils.GlideImageLoader
import com.github.popular.utils.ImageLoader
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val MAIN_SCHEDULER_NAME = "MainScheduler"
private const val NETWORK_SCHEDULER_NAME = "NetworkScheduler"
val appModule = module {
    single<ImageLoader> {
        GlideImageLoader()
    }
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

    single<GetRepositoryUseCase> {
        GetRepositoryUseCaseImpl(reposRepository = get())
    }
    single<Scheduler>(named(MAIN_SCHEDULER_NAME)) {
        AndroidSchedulers.mainThread()
    }
    single(named(NETWORK_SCHEDULER_NAME)) {
        Schedulers.io()
    }

    viewModel {
        PopularListingViewModel(
            popularListingUseCase = get(),
            mainScheduler = get(named(MAIN_SCHEDULER_NAME)),
            networkScheduler = get(named(NETWORK_SCHEDULER_NAME))
        )
    }

    viewModel {
        RepositoryDetailsViewModel(
            detailsUseCase = get(),
            mainScheduler = get(named(MAIN_SCHEDULER_NAME)),
            networkScheduler = get(named(NETWORK_SCHEDULER_NAME))
        )
    }
}