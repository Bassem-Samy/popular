package com.github.popular.repository

import arrow.core.Option
import com.github.popular.domain.model.Repository
import com.github.popular.network.PopularRepositoriesResponse

class RepositoryDomainMapper {
    fun map(apiRepository: PopularRepositoriesResponse.ApiRepository): Repository {
        return Repository(
            id = apiRepository.id,
            name = apiRepository.name,
            owner = apiRepository.owner.loginName,
            starsCount = apiRepository.starsCount,
            ownerAvatar = apiRepository.owner.avatarUrl,
            description = Option.fromNullable(apiRepository.description)
        )
    }
}