package com.dynamicdal.simplenewsapp.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.dynamicdal.simplenewsapp.domain.repo.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    newsRepository: NewsRepository,
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    val newsPagingFlow = newsRepository.getNews().cachedIn(viewModelScope)

    fun onEvent(event: HomeEvent){
        when(event){
            is HomeEvent.UpdateScrollValue -> updateScrollValue(event.newValue)
            is HomeEvent.UpdateMaxScrollingValue -> updateMaxScrollingValue(event.newValue)
        }
    }
    private fun updateScrollValue(newValue: Int){
        _state.value = state.value.copy(scrollValue = newValue)
    }
    private fun updateMaxScrollingValue(newValue: Int){
        _state.value = state.value.copy(maxScrollingValue = newValue)
    }
}