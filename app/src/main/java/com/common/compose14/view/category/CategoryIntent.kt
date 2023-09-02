package com.common.compose14.view.category

import com.common.compose14.view.home.HomeIntent

sealed class CategoryIntent {
    object RequestCategory: CategoryIntent()
    object RequestCategoryContent: CategoryIntent()

}