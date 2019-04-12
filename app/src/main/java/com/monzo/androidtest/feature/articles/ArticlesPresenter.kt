package com.monzo.androidtest.feature.articles

import com.monzo.androidtest.common.BasePresenter
import com.monzo.androidtest.common.BasePresenterView
import com.monzo.androidtest.feature.articles.model.Article
import com.monzo.androidtest.feature.data.ArticlesRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ArticlesPresenter(private val articlesRepository: ArticlesRepository)
    : BasePresenter<ArticlesView>() {

    override fun register(view: ArticlesView) {
        super.register(view)

        addToUnsubscribe(view.onRefreshAction()
                .doOnNext { ignored -> view.showRefreshing(true) }
                .switchMapSingle<List<Article>> { ignored -> articlesRepository.latestFinTechArticles().subscribeOn(Schedulers.io()) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ articles ->
                    view.showRefreshing(false)
                    view.showArticles(articles)
                }, {
                    view.onError(it)
                })
        )

    }

    fun isFavorite(articleId: String) = articlesRepository.isFavorite(articleId)
}

interface ArticlesView : BasePresenterView {
    fun showRefreshing(isRefreshing: Boolean)
    fun showArticles(articles: List<Article>)

    fun onRefreshAction(): Observable<Unit>
    fun onError(throwable: Throwable)
}