package com.monzo.androidtest.feature.articles


import com.monzo.androidtest.api.GuardianService
import com.monzo.androidtest.api.model.ApiArticle
import com.monzo.androidtest.feature.articles.model.Article
import io.reactivex.Single


class ArticlesRepository(private val guardianService: GuardianService) {

    fun latestFinTechArticles(): Single<List<Article>> {
        return guardianService
                .searchArticles("fintech,brexit")
                .map { it.response.results }
                .map {
                    it.map { article ->
                        val thumbnail = article.fields?.thumbnail ?: ""
                        val headline = article.fields?.headline ?: ""

                        Article(article.id,
                                thumbnail,
                                article.sectionId,
                                article.sectionName,
                                article.webPublicationDate,
                                headline,
                                article.apiUrl)
                    }
                }
    }

    fun getArticle(articleUrl: String): Single<ApiArticle> {
        return guardianService.getArticle(articleUrl, "main,body,headline,thumbnail")
    }
}
