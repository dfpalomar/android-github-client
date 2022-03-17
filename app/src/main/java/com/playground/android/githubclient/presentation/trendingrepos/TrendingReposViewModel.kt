package com.playground.android.githubclient.presentation.trendingrepos

import com.playground.android.githubclient.core.BaseViewModel
import com.playground.android.githubclient.core.ViewEvent
import com.playground.android.githubclient.core.ViewSideEffect
import com.playground.android.githubclient.core.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrendingReposViewModel @Inject constructor(
) : BaseViewModel<ViewEvent, ViewState, ViewSideEffect>() {

  override fun setInitialState(): ViewState = object : ViewState {}
}