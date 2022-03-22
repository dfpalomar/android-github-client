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

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

  @Provides
  fun provideRoom(
    @ApplicationContext context: Context
  ): AppDatabase = Room.databaseBuilder(
    context,
    AppDatabase::class.java, "github_client_db"
  ).build()

  @Provides
  fun provideRepositoryDAO(
    db: AppDatabase
  ): RepositoryDAO = db.repositoryDao()
}
