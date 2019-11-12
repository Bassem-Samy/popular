package com.github.popular.ui.listing

import arrow.core.Option

data class PopularRepoListItem(
    val id: String,
    val name: String,
    val owner: String,
    val starsCount: Int,
    val ownerAvatar: String,
    val description: Option<String>
)