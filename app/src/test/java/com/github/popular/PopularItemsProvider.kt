package com.github.popular

import arrow.core.Option
import arrow.core.some
import com.github.popular.domain.model.Repository

object PopularItemsProvider {
    fun provideDomainRepos(): List<Repository> {
        return listOf(
            Repository(
                id = "1",
                name = "one",
                owner = "owner one",
                starsCount = 1,
                ownerAvatar = "avatar",
                description = "some description".some()
            ),
            Repository(
                id = "2",
                name = "two",
                owner = "owner 2",
                starsCount = 2123,
                ownerAvatar = "avatar2",
                description = "some description2".some()
            ),
            Repository(
                id = "3",
                name = "three",
                owner = "owner 3",
                starsCount = 111,
                ownerAvatar = "avatar3",
                description = Option.empty()
            )
        )
    }
}