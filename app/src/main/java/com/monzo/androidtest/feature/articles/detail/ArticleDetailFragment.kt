package com.monzo.androidtest.feature.articles.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.monzo.androidtest.R
import com.monzo.androidtest.common.BaseFragment
import com.monzo.androidtest.databinding.FragmentArticleDetailBinding
import com.monzo.androidtest.feature.articles.model.Article
import io.reactivex.Observable
import org.koin.android.ext.android.inject

class ArticleDetailFragment : BaseFragment(), ArticleDetailView {
    val args: ArticleDetailFragmentArgs by navArgs()

    private val presenter: ArticleDetailPresenter by inject()

    private lateinit var binding: FragmentArticleDetailBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_article_detail, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticleDetailBinding.bind(view).also {
            it.fragment = this
        }
        presenter.register(this)
        presenter.loadArticle(args.articleUrl)
    }

    override fun showProgress(isInProgress: Boolean) {
        binding.isInProgress = isInProgress
    }

    override fun showArticle(article: Article) {
        binding.imageUrl = article.thumbnail
        binding.title = article.title
        binding.content = article.body
    }

    override fun onArticleFavorited(): Observable<Article> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}