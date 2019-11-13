package com.github.popular.domain.usecase

import com.github.popular.domain.model.Repository
import com.github.popular.repository.ReposRepository
import io.reactivex.Single

class GetRepositoryUseCaseImpl(private val reposRepository: ReposRepository) : GetRepositoryUseCase {
    override fun getRepositoryById(id: String): Single<Repository> {
        return reposRepository.getRepositoryById(id)
    }
}