package com.monzo.androidtest.feature.data

/**
 * In-memory local store
 */
//todo: implement with db like Room(SQLite) or Realm.
class ArticlesLocalStore {
    private val favoriteArticleIds = HashSet<String>()

    fun addToFavorite(articleId: String) {
        favoriteArticleIds.add(articleId)
    }

    fun removeFromFavorites(articleId: String) {
        favoriteArticleIds.remove(articleId)
    }

    fun getFavoriteArticleIds() = favoriteArticleIds
}