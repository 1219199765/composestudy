package com.common.compose14

sealed class MoveIntent {

    data class MoveToDetails(val id:String):MoveIntent()
}