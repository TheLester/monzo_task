package com.monzo.androidtest.api.model

import org.threeten.bp.Instant

data class ApiArticle(
        val id: String,
        val sectionId: String,
        val sectionName: String,
        val webPublicationDate: Instant,
        val webTitle: String,
        val webUrl: String,
        val apiUrl: String,
        val fields: ApiArticleFields?
) 

