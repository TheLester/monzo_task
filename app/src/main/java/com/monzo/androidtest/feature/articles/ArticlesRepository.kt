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
                        transform(article)
                    }
                }
    }

    fun getArticle(articleUrl: String): Single<Article> {
        return guardianService
                .getArticle(articleUrl, "main,body,headline,thumbnail")
                .map { transform(it) }
    }

    private fun transform(article: ApiArticle): Article {
        val thumbnail = article.fields?.thumbnail ?: ""
        val headline = article.fields?.headline ?: ""

        val main = article.fields?.main ?: ""
        val body = article.fields?.body ?: ""


        return Article(article.id,
                article.sectionId,
                article.sectionName,
                article.webPublicationDate,
                article.apiUrl,
                thumbnail = thumbnail,
                title = headline,
                main = main,
                body = body
        )
    }
}
