package com.playground.android.githubclient.di

import android.content.Context
import androidx.room.Room
import com.playground.android.githubclient.data.AppDatabase
import com.playground.android.githubclient.data.RepositoryDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

private const val DATABASE_NAME = "github_android_client_db"

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

  @Provides
  fun provideRoom(
    @ApplicationContext context: Context
  ): AppDatabase = Room.databaseBuilder(
    context,
    AppDatabase::class.java, DATABASE_NAME
  ).build()

  @Provides
  fun provideRepositoryDAO(
    db: AppDatabase
  ): RepositoryDAO = db.repositoryDao()
}
