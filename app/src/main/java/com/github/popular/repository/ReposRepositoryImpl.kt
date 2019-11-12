package com.github.popular.repository

import com.github.popular.domain.Repository
import com.github.popular.network.RepositoriesApi
import io.reactivex.Single

class ReposRepositoryImpl(private val repositoriesApi: RepositoriesApi) : ReposRepository {
    override fun getRepositories(pageRequest: ReposRepository.RepositoryPageRequest): Single<List<Repository>> {
        return repositoriesApi.getPopularRepos(
            keyWord = pageRequest.keyword,
            page = pageRequest.page,
            pageSize = pageRequest.pageSize,
            sort = getApiSortValue(pageRequest.sort)
        ).map { response ->
            response.items.map { Repository(id = it.id) }
        }
    }

    private fun getApiSortValue(sortParams: ReposRepository.SortParams): String {
        return when (sortParams) {
            ReposRepository.SortParams.Stars -> "stars"
        }
    }
}