package com.playground.android.githubclient.di

import com.playground.android.githubclient.BuildConfig
import com.playground.android.githubclient.data.GithubApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Provides
  fun provideOkHttpClient(
    httpLoggingInterceptor: HttpLoggingInterceptor
  ): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(httpLoggingInterceptor)
    .build()

  @Provides
  fun provideMoshi(): Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

  @Provides
  fun provideLoggingInterceptor(): HttpLoggingInterceptor {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(if (BuildConfig.DEBUG) Level.BODY else Level.NONE)
    return loggingInterceptor
  }

  @Provides
  fun provideGithubApiClient(
    moshi: Moshi,
    httpClient: OkHttpClient
  ): GithubApi = Retrofit.Builder()
    .baseUrl("https://api.github.com/")
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .client(httpClient)
    .build()
    .create(GithubApi::class.java)
}
