package com.github.popular.repository

import com.github.popular.domain.Repository
import com.github.popular.network.PopularApi
import io.reactivex.Single

class PopularReposRepositoryImpl(private val popularApi: PopularApi) : PopularReposRepository {
    override fun getPopularRepositories(pageRequest: PopularReposRepository.RepositoryPageRequest): Single<List<Repository>> {
        return popularApi.getPopularRepos(
            keyWord = pageRequest.keyword,
            page = pageRequest.page,
            pageSize = pageRequest.pageSize,
            sort = getApiSortValue(pageRequest.sort)
        ).map { response ->
            response.items.map { Repository(id = it.id) }
        }
    }

    private fun getApiSortValue(sortParams: PopularReposRepository.SortParams): String {
        return when (sortParams) {
            PopularReposRepository.SortParams.Stars -> "stars"
        }
    }
}