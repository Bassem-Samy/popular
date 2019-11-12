package com.github.popular

import com.github.popular.domain.usecase.PopularListingUseCaseImpl
import com.github.popular.domain.usecase.PopularReposPageRequest
import com.github.popular.repository.ReposRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito.`when`

class PopularListingUseCaseImplTest {
    private val mockRepository: ReposRepository = mock()
    private val sut = PopularListingUseCaseImpl(reposRepository = mockRepository)

    @Test
    fun `calls repository with parameters when gets popular items`() {
        val repositoryPageRequest =
            ReposRepository.RepositoryPageRequest(
                keyword = "SomeKeyWord",
                page = 1,
                pageSize = 200,
                sort = ReposRepository.SortParams.Stars
            )
        `when`(mockRepository.getRepositories(repositoryPageRequest)).thenReturn(Single.just(mock()))
        sut.getPopularRepos(
            PopularReposPageRequest(
                keyword = "SomeKeyWord",
                page = 1,
                pageSize = 200
            )
        )
        verify(mockRepository).getRepositories(repositoryPageRequest)
    }

    @Test
    fun `return correct items`() {
        val repositoryPageRequest =
            ReposRepository.RepositoryPageRequest(
                keyword = "SomeKeyWord",
                page = 1,
                pageSize = 200,
                sort = ReposRepository.SortParams.Stars
            )
        val expected = PopularItemsProvider.provideDomainRepos()
        `when`(mockRepository.getRepositories(repositoryPageRequest)).thenReturn(Single.just(expected))

        val actual = sut.getPopularRepos(
            PopularReposPageRequest(
                keyword = "SomeKeyWord",
                page = 1,
                pageSize = 200
            )
        ).test().values().first()
        assertEquals(expected, actual)
    }
}
