package com.github.popular.network

import com.github.popular.BuildConfig
import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single {
        OkHttpClient.Builder().build()

    }

    single(named("base_url")) {
        BuildConfig.BASE_URL
    }

    single<CallAdapter.Factory> { RxJava2CallAdapterFactory.create() }
    single { Gson() }

    single<Converter.Factory> { GsonConverterFactory.create(get()) }

    single {
        Retrofit.Builder()
            .baseUrl(get<String>(named("base_url")))
            .addCallAdapterFactory(get())
            .addConverterFactory(get())
            .client(get())
            .build()
    }
    single {
        get<Retrofit>().create(RepositoriesApi::class.java)
    }
}