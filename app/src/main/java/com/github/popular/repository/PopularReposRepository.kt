package com.github.popular.repository

import com.github.popular.domain.Repository
import io.reactivex.Single

interface PopularReposRepository {
    data class RepositoryPageRequest(
        val keyword: String,
        val page: Int,
        val pageSize: Int,
        val sort: SortParams
    )

    sealed class SortParams {
        object Stars : SortParams()
    }

    fun getPopularRepositories(pageRequest: RepositoryPageRequest): Single<List<Repository>>
}