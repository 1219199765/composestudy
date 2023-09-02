package com.common.compose14.view.search

import HomeBean
import com.common.compose14.common.room.Search

sealed class SearchState {
    object OnLoading: SearchState()

    data class RequestRecordsSus(val data: MutableList<Search>): SearchState()
    data class RequestRecordsErr(val data:Exception): SearchState()
}