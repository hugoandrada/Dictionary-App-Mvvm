package com.hugo.andrada.dev.dictionaryappmvvm.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hugo.andrada.dev.dictionaryappmvvm.core.ResultState
import com.hugo.andrada.dev.dictionaryappmvvm.domain.use_cases.GetWordsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWords: GetWordsUseCase
): ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchJob: Job? = null

    fun onSearch(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            getWords(query).onEach { result ->
                when(result) {
                    is ResultState.Loading -> {
                        _state.value = HomeState(
                            words = result.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                    is ResultState.Success -> {
                        _state.value = HomeState(
                            words = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is ResultState.Error -> {
                        _state.value = HomeState(
                            words = result.data ?: emptyList(),
                            isLoading = false
                        )
                        _eventFlow.emit(UiEvent.ShowSnackBar(
                            result.message ?: "unknown error"
                        ))
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String): UiEvent()
    }
}