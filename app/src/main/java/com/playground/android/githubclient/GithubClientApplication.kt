package com.playground.android.githubclient

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GithubClientApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    AndroidThreeTen.init(this)
  }
}

