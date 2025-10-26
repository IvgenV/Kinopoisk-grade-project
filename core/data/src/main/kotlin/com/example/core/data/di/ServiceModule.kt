package com.example.core.data.di

import com.example.core.data.network.MovieService
import com.example.kinopoisk.core.data.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Singleton
    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieService =
        retrofit.create(MovieService::class.java)

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().apply {
        addLast(KotlinJsonAdapterFactory())
    }.build()

    @Provides
    @Singleton
    fun provideRetrofit(
        moshi: Moshi,
        okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder().apply {
        baseUrl(BuildConfig.BASE_URL)
        client(okHttpClient)
        addConverterFactory(MoshiConverterFactory.create(moshi))
    }.build()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: Interceptor
    ) = OkHttpClient.Builder().addInterceptor(interceptor).build()

    @Provides
    @Singleton
    fun provideInterceptor() = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val requestWithHeader = originalRequest.newBuilder()
                .header("X-API-KEY", BuildConfig.KINOPOISK_API_KEY)
                .build()
            return chain.proceed(requestWithHeader)
        }

    }

}