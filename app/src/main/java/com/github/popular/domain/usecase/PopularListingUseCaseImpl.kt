package com.github.popular.domain.usecase

import com.github.popular.domain.model.Repository
import com.github.popular.repository.ReposRepository
import io.reactivex.Single

class PopularListingUseCaseImpl(private val reposRepository: ReposRepository) : PopularListingUseCase {
    override fun getPopularRepos(popularReposPageRequest: PopularReposPageRequest): Single<List<Repository>> {
        return reposRepository.getRepositories(
            pageRequest = ReposRepository.RepositoryPageRequest(
                keyword = popularReposPageRequest.keyword,
                page = popularReposPageRequest.page,
                pageSize = popularReposPageRequest.pageSize,
                sort = ReposRepository.SortParams.Stars
            )
        )
    }
}