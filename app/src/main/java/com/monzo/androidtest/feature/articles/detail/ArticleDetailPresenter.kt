package com.monzo.androidtest.feature.articles.detail

import com.monzo.androidtest.common.BasePresenter
import com.monzo.androidtest.common.BasePresenterView
import com.monzo.androidtest.feature.articles.ArticlesRepository
import com.monzo.androidtest.feature.articles.model.Article
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ArticleDetailPresenter(private val articlesRepository: ArticlesRepository)
    : BasePresenter<ArticleDetailView>() {


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
                        .subscribe { article -> view?.showArticle(article) }
        )
    }
}

interface ArticleDetailView : BasePresenterView {
    fun showProgress(isInProgress: Boolean)
    fun showArticle(article: Article)

    fun onArticleFavorited(): Observable<Article>
}