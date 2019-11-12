package com.github.popular.domain.model

import arrow.core.Option

data class Repository(
    val id: String,
    val name: String,
    val owner: String,
    val starsCount: Int,
    val ownerAvatar: String,
    val description: Option<String>
)