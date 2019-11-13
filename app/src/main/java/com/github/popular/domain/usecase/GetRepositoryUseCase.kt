package com.github.popular.domain.usecase

import com.github.popular.domain.model.Repository
import io.reactivex.Single

interface GetRepositoryUseCase{
    fun getRepositoryById(id:String): Single<Repository>
}