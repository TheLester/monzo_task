package com.monzo.androidtest.feature.articles.detail

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableBoolean
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.monzo.androidtest.R
import com.monzo.androidtest.common.BaseFragment
import com.monzo.androidtest.databinding.FragmentArticleDetailBinding
import com.monzo.androidtest.feature.articles.model.Article
import kotlinx.android.synthetic.main.fragment_article_detail.*
import org.koin.android.ext.android.inject

class ArticleDetailFragment : BaseFragment(), ArticleDetailView {

    val args: ArticleDetailFragmentArgs by navArgs()

    private val presenter: ArticleDetailPresenter by inject()
    private lateinit var binding: FragmentArticleDetailBinding

    val isFavorite = ObservableBoolean()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_article_detail, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticleDetailBinding.bind(view).also {
            it.fragment = this
            it.content = ""
        }
        contentTextView.movementMethod = LinkMovementMethod.getInstance()

        toolbar.setNavigationOnClickListener { it.findNavController().popBackStack() }

        presenter.register(this)
        presenter.loadArticle(args.articleUrl)
    }

    override fun showProgress(isInProgress: Boolean) {
        binding.isInProgress = isInProgress
    }

    override fun showArticle(article: Article, isFavorite: Boolean) {
        binding.imageUrl = article.thumbnail
        binding.title = article.title
        binding.content = article.body

        this.isFavorite.set(isFavorite)
    }

    fun onClickFavorite(view: View) {
        presenter.updateFavorite(isFavorite.get())

        Snackbar.make(view,
                if (isFavorite.get()) getString(R.string.article_detail_favorite_added)
                else getString(R.string.article_detail_favorite_removed),
                Snackbar.LENGTH_LONG
        ).show()
    }

    override fun onError(throwable: Throwable) {
        handleError(throwable)
    }
}