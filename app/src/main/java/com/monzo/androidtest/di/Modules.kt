package com.monzo.androidtest.di

import com.google.gson.Gson
import com.monzo.androidtest.HeadlinesApp
import com.monzo.androidtest.R
import com.monzo.androidtest.api.GuardianService
import com.monzo.androidtest.feature.articles.ArticlesPresenter
import com.monzo.androidtest.feature.articles.ArticlesRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val articlesModule = module(override = true) {
    factory { ArticlesPresenter(get()) }
    single { ArticlesRepository(get()) }

}

internal const val BASE_URL = "https://content.guardianapis.com"
internal const val HEADER_API_KEY = "api-key"

val networkModule = module(override = true) {

    single<GuardianService> {
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(get()))
                .client(get())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(GuardianService::class.java)
    }

    single<OkHttpClient> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val authInterceptor = Interceptor { chain ->
            val original = chain.request()
            val hb = original.headers().newBuilder()
            hb.add(HEADER_API_KEY, HeadlinesApp.get().getString(R.string.guardian_api_key))
            chain.proceed(original.newBuilder().headers(hb.build()).build())
        }

        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor(authInterceptor)
        clientBuilder.addInterceptor(loggingInterceptor)
        clientBuilder.build()
    }

    single { Gson() }
}

val modules = listOf(articlesModule, networkModule)