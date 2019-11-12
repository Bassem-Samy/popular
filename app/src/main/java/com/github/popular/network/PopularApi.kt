package com.github.popular.network

import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface PopularApi {
    @GET("repositories")
    fun getPopularRepos(
        @Query("q") keyWord: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int,
        @Query("sort") sort: String
    ): Single<PopularRepositoriesResponse>
}

data class PopularRepositoriesResponse(
    @SerializedName("items") val items: List<ApiRepository>
) {
    data class ApiRepository(
        @SerializedName("id") val id: String
    )
}