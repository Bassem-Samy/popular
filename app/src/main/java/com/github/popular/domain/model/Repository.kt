package com.github.popular.domain.model

import arrow.core.Option

data class Repository(
    val id: String,
    val name: String,
    val owner: String,
    val starsCount: Int,
    val forksCount: Int,
    val openIssuesCount: Int,
    val ownerAvatar: String,
    val language: Option<String>,
    val description: Option<String>
)