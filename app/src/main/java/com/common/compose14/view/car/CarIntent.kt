package com.common.compose14.view.car

sealed class CarIntent {
    object RequestCarList:CarIntent()
    data class RequestChecked(val index:Int):CarIntent()
    data class RequestAddOrRemoveNum(val isAdd:Boolean,val index:Int):CarIntent()
    object  RequestAllChecked:CarIntent()
}