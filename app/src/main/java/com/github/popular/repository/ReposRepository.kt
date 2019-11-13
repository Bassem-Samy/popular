package com.github.popular.repository

import com.github.popular.domain.model.Repository
import io.reactivex.Single

interface ReposRepository {
    data class RepositoryPageRequest(
        val keyword: String,
        val page: Int,
        val pageSize: Int,
        val sort: SortParams
    )

    sealed class SortParams {
        object Stars : SortParams()
    }

    fun getRepositories(pageRequest: RepositoryPageRequest): Single<List<Repository>>
    fun getRepositoryById(id:String):Single<Repository>
}