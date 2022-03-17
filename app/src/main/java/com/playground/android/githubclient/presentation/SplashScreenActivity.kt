package com.playground.android.githubclient.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : androidx.activity.ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    MainActivity.start(this)
    finish()
  }
}
