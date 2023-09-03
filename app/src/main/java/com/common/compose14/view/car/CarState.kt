package com.common.compose14.view.car

import com.common.compose14.bean.CarBean

sealed class CarState  {
    object OnLoading:CarState()

    data class RequestCarListSus(val data:MutableList<CarBean>):CarState()
    data class RequestCarListErr(val e:Exception):CarState()
}