package com.dynamicdal.simplenewsapp.ui.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

interface UIEventsHandler<E> {
  val events: Flow<E>
  fun emit(e: E)
}

/**
 * Consumes the UI events from the Compose & stores it in a Flow.
 * E --> It's  a sealed class of UI events for the Compose view.
 */
class UIEventsHandlerAsUnifiedFlow<E> : UIEventsHandler<E> {
  private val _events: MutableSharedFlow<E> = MutableSharedFlow()
  override val events: Flow<E> = _events.asSharedFlow()

  override fun emit(e: E) {
    _events.tryEmit(e)
  }
}

open class UpstreamBridge<ComposeUIEvents>(
  private val uiEventsHandler: UIEventsHandler<ComposeUIEvents> = UIEventsHandlerAsUnifiedFlow(),
) {
  protected val events: Flow<ComposeUIEvents> = uiEventsHandler.events
}