package com.monzo.androidtest.feature.articles

import com.monzo.androidtest.common.BasePresenter
import com.monzo.androidtest.common.BasePresenterView
import com.monzo.androidtest.feature.articles.model.Article
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ArticlesPresenter(private val articlesRepository: ArticlesRepository)
    : BasePresenter<ArticlesView>() {

    override fun register(view: ArticlesView) {
        super.register(view)

        // TODO: error handling
        addToUnsubscribe(view.onRefreshAction()
                .doOnNext { ignored -> view.showRefreshing(true) }
                .switchMapSingle<List<Article>> { ignored -> articlesRepository.latestFinTechArticles().subscribeOn(Schedulers.io()) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { articles ->
                    view.showRefreshing(false)
                    view.showArticles(articles)
                })

        addToUnsubscribe(view.onArticleClicked()
                .subscribe { (id, thumbnail, sectionId, sectionName, published, title, url) -> view.openArticleDetail() })
    }
}

interface ArticlesView : BasePresenterView {
    fun showRefreshing(isRefreshing: Boolean)
    fun showArticles(articles: List<Article>)

    fun onArticleClicked(): Observable<Article>
    fun onRefreshAction(): Observable<Any>

    fun openArticleDetail()
}