package com.common.compose14.view.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    /**
     * 接收事件
     */
    private val userIntent = MutableSharedFlow<HomeIntent>()

    /**
     * 分发用户事件
     * @param viewAction
     */
    fun dispatch(viewAction: HomeIntent) {
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
                    is HomeIntent.ReqestHome -> requestHome()
                    else -> {}
                }
            }
        }
    }

    /**
     * 控制页面切换是否重新执行网络请求
     * */
    var repeatRequest by mutableStateOf(true)

    var categoryIndex by mutableStateOf(0)

    private var _state = MutableStateFlow<HomeState>(HomeState.OnLoading)
    val state: StateFlow<HomeState> = _state.asStateFlow()


    private fun requestHome() {
        viewModelScope.launch {
            HomeRepository.requestHome().collect {
                _state.emit(it)
            }
        }
    }

}