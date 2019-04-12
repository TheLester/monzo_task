package com.monzo.androidtest.feature.data

import com.monzo.androidtest.api.GuardianService
import com.monzo.androidtest.api.model.ApiArticle
import io.reactivex.Single

class ArticlesRemoteDataSource(
        private val guardianService: GuardianService
) {

    fun searchArticles(searchTerm: String): Single<List<ApiArticle>> {
        return guardianService.searchArticles(searchTerm).map { it.response.results }
    }

    fun fetchArticle(articleUrl: String): Single<ApiArticle> {
        return guardianService
                .getArticle(articleUrl, "main,body,headline,thumbnail")
                .map { it.response.content }
    }
}