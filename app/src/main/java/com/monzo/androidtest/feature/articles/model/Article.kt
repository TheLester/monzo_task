package com.monzo.androidtest.feature.articles.model

import org.threeten.bp.Instant

data class Article(
        val id: String,
        val sectionId: String,
        val sectionName: String,
        val published: Instant,
        val url: String,

        val thumbnail: String?,
        val title: String?,

        val main: String?,
        val body: String?
)