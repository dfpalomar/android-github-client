package com.playground.android.githubclient.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

interface ViewState
interface ViewEvent

abstract class BaseViewModel<Event : ViewEvent, UiState : ViewState> :
  ViewModel() {

  private val initialState: UiState by lazy { setInitialState() }

  protected abstract fun setInitialState(): UiState
  protected open fun handleEvents(event: Event) {}

  private val _viewState by lazy { MutableStateFlow(initialState) }
  val viewState: StateFlow<UiState> by lazy { _viewState }

  private val _event: MutableSharedFlow<Event> = MutableSharedFlow()

  init {
    subscribeToEvents()
  }

  private fun subscribeToEvents() {
    viewModelScope.launch {
      _event.onEach { handleEvents(it) }.collect()
    }
  }

  fun setEvent(event: Event) {
    viewModelScope.launch { _event.emit(event) }
  }

  protected fun setState(reducer: UiState.() -> UiState) {
    viewModelScope.launch {
      _viewState.emit(_viewState.value.reducer())
    }
  }
}
