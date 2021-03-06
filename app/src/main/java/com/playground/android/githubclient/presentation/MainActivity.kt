package com.playground.android.githubclient.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.playground.android.githubclient.ui.theme.GithubClientTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      GithubClientTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
          Navigation()
        }
      }
    }
  }

  companion object {
    fun start(context: Context) {
      context.startActivity(Intent(context, MainActivity::class.java))
    }
  }
}
