package com.github.popular.domain.usecase

import com.github.popular.domain.model.Repository
import io.reactivex.Single

interface PopularListingUseCase {
    fun getPopularRepos(popularReposPageRequest: PopularReposPageRequest): Single<List<Repository>>
}

data class PopularReposPageRequest(
    val keyword: String,
    val page: Int,
    val pageSize: Int
)