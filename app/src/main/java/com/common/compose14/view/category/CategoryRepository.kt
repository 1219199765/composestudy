package com.common.compose14.view.category

import MainService
import MyService2
import kotlinx.coroutines.flow.Flow

object CategoryRepository {

    suspend fun requestCategory(): Flow<CategoryState> {
        // 查询数据库是否存在，如果不存在请求网络

        val params = mutableMapOf<String, String>()
        params["storeId"] = "39179"
        params["_timestemp"] =  "1693472819438"

        // 请求网络
        return MainBusiness.requestCategory { MyService2.requestCategory(params) }
    }



    //47336
    suspend fun requestCategoryContent(id:String): Flow<CategoryState> {
        // 查询数据库是否存在，如果不存在请求网络

        val params = mutableMapOf<String, String>()
        params["storeId"] = "39179"
        params["_timestemp"] =  "1693472819438"

        params["id"] = id
        params["type"] = "CATEGORY"
        params["rows"] = "10"
        params["keyword"] = ""


        // 请求网络
        return MainBusiness.requestCategoryContent { MyService2.requestCategoryContent(params) }
    }
}