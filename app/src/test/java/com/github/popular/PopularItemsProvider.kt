package com.github.popular

import android.icu.text.LocaleDisplayNames
import arrow.core.Option
import arrow.core.some
import com.github.popular.domain.model.Repository
import com.github.popular.ui.listing.PopularRepoListItem

object PopularItemsProvider {
    fun provideDomainRepos(): List<Repository> {
        return listOf(
            Repository(
                id = "1",
                name = "one",
                owner = "owner one",
                starsCount = 1,
                forksCount = 213,
                openIssuesCount = 100,
                ownerAvatar = "avatar",
                language = "Kotlin".some(),
                description = "some description".some()
            ),
            Repository(
                id = "2",
                name = "two",
                owner = "owner 2",
                starsCount = 2123,
                forksCount = 2,
                openIssuesCount = 3,
                ownerAvatar = "avatar2",
                language = Option.empty(),
                description = "some description2".some()
            ),
            Repository(
                id = "3",
                name = "three",
                owner = "owner 3",
                starsCount = 111,
                forksCount = 33,
                openIssuesCount = 210,
                ownerAvatar = "avatar3",
                language = "Java".some(),
                description = Option.empty()
            )
        )
    }

    fun providePopularRepoListItems(): List<PopularRepoListItem> {
        return listOf(
            PopularRepoListItem(
                id = "1",
                name = "one",
                owner = "owner one",
                starsCount = 1,
                ownerAvatar = "avatar",
                description = "some description".some()
            ),
            PopularRepoListItem(
                id = "2",
                name = "two",
                owner = "owner 2",
                starsCount = 2123,
                ownerAvatar = "avatar2",
                description = "some description2".some()
            ),
            PopularRepoListItem(
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