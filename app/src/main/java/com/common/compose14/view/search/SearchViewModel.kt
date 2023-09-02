package com.common.compose14.view.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.common.compose14.common.room.Search
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel:ViewModel() {


    /**
     * 接收事件
     */
    private val userIntent = MutableSharedFlow<SearchIntent>()

    /**
     * 分发用户事件
     * @param viewAction
     */
    fun dispatch(viewAction: SearchIntent) {
        try {
            viewModelScope.launch {
                userIntent.emit(viewAction)
            }
        } catch (e: Exception) {
        }
    }

    init {
        viewModelScope.launch {
            userIntent.collect {
                when (it) {
                    is SearchIntent.RequestAllRecords -> requestAllRecords()
                    is SearchIntent.RequestAddRecords -> requestAddRecords()
                    is SearchIntent.RequestDeleteAll -> requestDeleteAll()
                    else -> {}
                }
            }
        }
    }

    private var _state = MutableStateFlow<SearchState>(SearchState.OnLoading)
    val state: StateFlow<SearchState> = _state.asStateFlow()

    var currentSearch by mutableStateOf(Search())


    private fun requestAllRecords(){
        viewModelScope.launch {
            _state.emit(SearchState.RequestRecordsSus(SearchRepository.requestAllRecords()))
        }
    }

    private fun requestAddRecords(){
        viewModelScope.launch {
            currentSearch?.let { SearchRepository.requestAddRecords(it) }
            requestAllRecords()
        }
    }

    private fun requestDeleteAll(){
        viewModelScope.launch {
            SearchRepository.requestDeleteAll()
            requestAllRecords()
        }
    }
}