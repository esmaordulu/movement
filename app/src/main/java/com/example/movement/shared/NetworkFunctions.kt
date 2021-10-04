package com.example.movement.shared

import androidx.viewbinding.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Config.BaseUrl)
        .client(provideClient())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
}

private fun provideClient(): OkHttpClient {
    val logInterceptor = HttpLoggingInterceptor().apply {
        if (BuildConfig.DEBUG) {
            this.level = HttpLoggingInterceptor.Level.HEADERS
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    }
    return OkHttpClient.Builder()
        .connectTimeout(Config.Timeout, TimeUnit.SECONDS)
        .readTimeout(Config.Timeout, TimeUnit.SECONDS)
        .addInterceptor(logInterceptor)
        .build()
}


/* function to build our Retrofit service */
inline fun <reified T> createWebService(retrofit: Retrofit): T {
    return retrofit.create(T::class.java)
}