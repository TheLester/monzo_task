package com.monzo.androidtest.feature.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.monzo.androidtest.R
import com.monzo.androidtest.common.BaseEpoxyFragment
import com.monzo.androidtest.common.simpleController
import com.monzo.androidtest.feature.articles.model.Article
import com.monzo.androidtest.itemArticle
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_article_list.*
import org.koin.android.ext.android.inject

class ArticlesFragment : BaseEpoxyFragment(), ArticlesView {
    private val presenter: ArticlesPresenter by inject()
    private val articles = mutableListOf<Article>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_article_list, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.register(this)

    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }

    override fun epoxyController() = simpleController {
        articles.forEach { article ->
            itemArticle {
                id(article.id)
                thumbnail(article.thumbnail)
                headline(article.title)
            }
        }
    }

    override fun epoxyRecyclerView() = articlesRecyclerview

    override fun onArticleClicked(): Observable<Article> {
        // TODO: return the article clicked on
        return Observable.empty()
    }

    override fun showArticles(articles: List<Article>) {

        with(this.articles) {
            clear()
            addAll(articles)
        }
        epoxyController.requestModelBuild()
    }

    override fun onRefreshAction(): Observable<Any> {
        return Observable.just(Unit)
//        return Observable.create<Any> { emitter ->
//            swipeRefreshLayout!!.setOnRefreshListener { emitter.onNext(null) }
//            emitter.setCancellable { swipeRefreshLayout!!.setOnRefreshListener(null) }
//        }.startWith(Event.IGNORE)
    }

    override fun openArticleDetail() {
    }

    override fun showRefreshing(isRefreshing: Boolean) {
        articlesSwipeRefreshLayout!!.isRefreshing = isRefreshing
    }
}
