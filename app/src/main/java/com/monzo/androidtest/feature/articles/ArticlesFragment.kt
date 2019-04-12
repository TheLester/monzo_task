package com.monzo.androidtest.feature.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.monzo.androidtest.ItemArticleBindingModel_
import com.monzo.androidtest.R
import com.monzo.androidtest.common.BaseEpoxyFragment
import com.monzo.androidtest.common.epoxy.CardGroup
import com.monzo.androidtest.common.extension.asReadableString
import com.monzo.androidtest.common.simpleController
import com.monzo.androidtest.feature.articles.model.Article
import com.monzo.androidtest.itemHeader
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_article_list.*
import org.koin.android.ext.android.inject
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.temporal.WeekFields


class ArticlesFragment : BaseEpoxyFragment(),
        ArticlesView, SwipeRefreshLayout.OnRefreshListener {

    private val presenter: ArticlesPresenter by inject()
    private val articles = mutableListOf<Article>()

    private var currentWeek: Int = 0
    private val onRefreshAction: PublishSubject<Unit> = PublishSubject.create()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_article_list, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentWeek = LocalDate.now().get(WeekFields.ISO.weekOfYear())

        presenter.register(this)
        articlesSwipeRefreshLayout.setOnRefreshListener(this)
        onRefreshAction.onNext(Unit)
    }

    override fun onDestroyView() {
        presenter.unregister()
        super.onDestroyView()
    }

    override fun onError(throwable: Throwable) {
        handleError(throwable)
    }

    override fun epoxyController() = simpleController {

        // display favorites first

        articles.filter { presenter.isFavorite(it.id) }.let {
            if (it.isEmpty()) return@let

            itemHeader {
                id("Favourites")
                headerText(getString(R.string.article_list_favourites))
            }

            CardGroup(getArticleModels(it)).addTo(this)
        }

        // display current,last week and earlier articles
        val groupedArticles = articles
                .filter { !presenter.isFavorite(it.id) }
                .sortedByDescending { it.published.epochSecond }
                .groupBy {

                    val articleWeek = it.published
                            .atZone(ZoneId.systemDefault())
                            .get(WeekFields.ISO.weekOfYear())

                    when (articleWeek) {
                        currentWeek -> getString(R.string.article_list_week_current)
                        (currentWeek - 1) -> getString(R.string.article_list_week_last)
                        else -> getString(R.string.article_list_week_earlier)
                    }
                }

        groupedArticles.keys.forEach {
            itemHeader {
                id(it)
                headerText(it)
            }

            CardGroup(getArticleModels(groupedArticles[it]))
                    .addTo(this)
        }


    }

    private fun getArticleModels(articles: List<Article>?): List<DataBindingEpoxyModel> {
        val models = mutableListOf<DataBindingEpoxyModel>()
        articles?.forEach { article ->
            models.add(
                    ItemArticleBindingModel_()
                            .id(article.id)
                            .thumbnail(article.thumbnail)
                            .headline(article.title)
                            .date(article.published.asReadableString())
                            .clickListener(View.OnClickListener {
                                val direction = ArticlesFragmentDirections.actionArticlesToDetail(article.url)
                                it.findNavController().navigate(direction)
                            })
            )
        }
        return models
    }

    override fun epoxyRecyclerView() = articlesRecyclerview

    override fun showArticles(articles: List<Article>) {

        with(this.articles) {
            clear()
            addAll(articles)
        }
        epoxyController.requestModelBuild()
    }

    override fun onRefresh() {
        onRefreshAction.onNext(Unit)
    }

    override fun onRefreshAction(): Observable<Unit> {
        return onRefreshAction.share()
    }

    override fun showRefreshing(isRefreshing: Boolean) {
        articlesSwipeRefreshLayout!!.isRefreshing = isRefreshing
    }
}
