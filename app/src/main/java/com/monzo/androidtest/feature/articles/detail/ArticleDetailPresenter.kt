package com.monzo.androidtest.feature.articles.detail

import com.monzo.androidtest.common.BasePresenter
import com.monzo.androidtest.common.BasePresenterView
import com.monzo.androidtest.feature.articles.model.Article
import com.monzo.androidtest.feature.data.ArticlesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ArticleDetailPresenter(private val articlesRepository: ArticlesRepository)
    : BasePresenter<ArticleDetailView>() {

    private var articleId: String? = null

    fun loadArticle(url: String) {

        addToUnsubscribe(
                articlesRepository.getArticle(url)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe {
                            view?.showProgress(true)
                        }
                        .doAfterTerminate {
                            view?.showProgress(false)
                        }
                        .subscribe({ article ->
                            articleId = article.id
                            view?.showArticle(article, articlesRepository.isFavorite(article.id))
                        }, {})
        )
    }

    fun updateFavorite(isFavorite: Boolean) {
        articleId?.let {
            if (isFavorite) articlesRepository.favoriteArticle(articleId!!)
            else articlesRepository.removeFromFavoriteArticle(articleId!!)
        }
    }
}

interface ArticleDetailView : BasePresenterView {
    fun showProgress(isInProgress: Boolean)
    fun showArticle(article: Article, isFavorite: Boolean)
    fun onError(throwable: Throwable)
}