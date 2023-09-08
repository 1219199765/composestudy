package com.common.compose14.view.home

import com.common.compose14.bean.HomeBean


sealed class HomeState {
    object OnLoading:HomeState()

    data class RequestHomeSus(val data: HomeBean):HomeState()
    data class RequestHomeErr(val data:Exception):HomeState()


}