package com.github.popular.network

import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface RepositoriesApi {
    @GET("search/repositories")
    fun getPopularRepos(
        @Query("q") keyWord: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int,
        @Query("sort") sort: String
    ): Single<PopularRepositoriesResponse>

    //@Headers("Authorization: token ADD_YOUR_ACCESS_TOKEN_HERE")
    @GET("repositories/{id}")
    fun getRepositoryDetailsById(@Path("id") id: String): Single<PopularRepositoriesResponse.ApiRepository>
}

data class PopularRepositoriesResponse(
    @SerializedName("items") val items: List<ApiRepository>
) {
    data class ApiRepository(
        @SerializedName("id") val id: String,
        @SerializedName("name") val name: String,
        @SerializedName("description") val description: String?,
        @SerializedName("language") val language: String?,
        @SerializedName("stargazers_count") val starsCount: Int,
        @SerializedName("forks") val forksCount: Int,
        @SerializedName("open_issues") val openIssuesCount: Int,
        @SerializedName("owner")
        val owner: ApiRepositoryOwner
    ) {
        data class ApiRepositoryOwner(
            @SerializedName("login") val loginName: String,
            @SerializedName("avatar_url") val avatarUrl: String
        )
    }
}