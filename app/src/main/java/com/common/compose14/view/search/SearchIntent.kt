package com.common.compose14.view.search


sealed class SearchIntent {
    object RequestAllRecords: SearchIntent()

    object RequestAddRecords: SearchIntent()
    object RequestDeleteAll: SearchIntent()

}