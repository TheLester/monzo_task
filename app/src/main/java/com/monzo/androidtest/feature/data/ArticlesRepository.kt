package com.monzo.androidtest.feature.data


import com.monzo.androidtest.api.model.ApiArticle
import com.monzo.androidtest.feature.articles.model.Article
import io.reactivex.Single


class ArticlesRepository(
        private val localStore: ArticlesLocalStore,
        private val remoteDataSource: ArticlesRemoteDataSource
) {

    fun latestFinTechArticles(): Single<List<Article>> {
        return remoteDataSource
                .searchArticles("fintech,brexit")
                .map {
                    it.map { article ->
                        transform(article)
                    }
                }
    }

    fun getArticle(articleUrl: String): Single<Article> =
            remoteDataSource.fetchArticle(articleUrl)
                    .map { transform(it) }


    fun favoriteArticle(articleId: String) =
            localStore.addToFavorite(articleId)


    fun removeFromFavoriteArticle(articleId: String) =
            localStore.removeFromFavorites(articleId)


    fun isFavorite(articleId: String): Boolean =
            localStore.getFavoriteArticleIds().contains(articleId)

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
