package com.common.compose14.view.category

import LogUtils
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.biubiu.eventbus.observe.observeEvent
import com.common.compose14.common.event.AppMoveEvent
import com.common.compose14.common.event.AppScopeEvent
import com.common.compose14.view.home.HomeIntent
import com.common.compose14.view.home.HomeState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CategoryViewModel:ViewModel() {

    /**
     * 接收事件
     */
    private val userIntent = MutableSharedFlow<CategoryIntent>()

    /**
     * 分发用户事件
     * @param viewAction
     */
    fun dispatch(viewAction: CategoryIntent) {
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
                    is CategoryIntent.RequestCategory -> requestCategory()
                    is CategoryIntent.RequestCategoryContent -> requestCategoryContent()
                    else -> {}
                }
            }
        }
        moveTo()
    }

    /**
     * 控制页面切换是否重新执行网络请求
     * */
    var repeatRequest by mutableStateOf(true)

    var leftIndex  by mutableIntStateOf(0)
    var topIndex by mutableIntStateOf(0)

    private var _state = MutableStateFlow<CategoryState>(CategoryState.OnLoading)
    val state: StateFlow<CategoryState> = _state.asStateFlow()

    private var _state2 = MutableStateFlow<CategoryState>(CategoryState.OnLoading)
    val state2: StateFlow<CategoryState> = _state2.asStateFlow()

    private fun requestCategory(){
        viewModelScope.launch {
            CategoryRepository.requestCategory().collect{
                _state.emit(it)
                requestCategoryContent()
            }
        }
    }

    private fun requestCategoryContent(){
        if (_state.value is CategoryState.RequestCategorySus) {
            val id  = (_state.value as CategoryState.RequestCategorySus).data.data?.get(leftIndex)?.children?.get(topIndex)?.id
                viewModelScope.launch {
                    CategoryRepository.requestCategoryContent(id.toString()).collect{
                        _state2.emit(it)
                    }
                }
        }
    }


    fun moveTo(){
        observeEvent<AppMoveEvent>(viewModelScope,isSticky = true) { value->
            LogUtils.d("2===>$leftIndex")
            leftIndex = value.tag
            LogUtils.d("3===>$leftIndex")
        }
    }
}