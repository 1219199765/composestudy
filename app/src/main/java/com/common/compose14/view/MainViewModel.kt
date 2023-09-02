package com.common.compose14.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biubiu.eventbus.observe.observeEvent
import com.common.compose14.common.event.AppMoveEvent

class MainViewModel:ViewModel() {
    init {
        moveTo()
    }
    var currentPage by mutableIntStateOf(0)

    fun moveTo(){
        observeEvent<AppMoveEvent>(viewModelScope) { value->
            currentPage = 1
        }
    }
}