package com.example.unspoken.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unspoken.data.repository.Feed
import com.example.unspoken.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FeedUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getMainFeeds().let { feeds ->
                _uiState.update {
                    it.copy(
                        mainFeed = feeds
                    )
                }
                Log.d("llll", uiState.value.mainFeed.toString())
            }
        }
    }
}

data class FeedUiState(
    val mainFeed: List<Feed> = mutableListOf()
)
