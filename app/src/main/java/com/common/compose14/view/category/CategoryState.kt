package com.common.compose14.view.category

import HomeBean
import com.common.compose14.bean.CategoryBean
import com.common.compose14.bean.CategoryContentBean
import com.common.compose14.view.home.HomeState

sealed class CategoryState {

    object OnLoading: CategoryState()

    data class RequestCategorySus(val data: CategoryBean): CategoryState()
    data class RequestCategoryErr(val data:Exception): CategoryState()


    data class RequestCategoryContentSus(val data: CategoryContentBean): CategoryState()
    data class RequestCategoryContentErr(val data:Exception): CategoryState()
}