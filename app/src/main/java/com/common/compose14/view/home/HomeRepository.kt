package com.common.compose14.view.home

import MainService
import kotlinx.coroutines.flow.Flow

object HomeRepository {

    suspend fun requestHome(): Flow<HomeState> {
        // 查询数据库是否存在，如果不存在请求网络

        val params = mutableMapOf<String, String>()
//        params["queryFuture"] = "Y"
//        params["storeId"] = "39179"
//        params["layoutIds"] = "64354%2C64355%2C64356%2C66123%2C64360%2C64358%2C64361%2C64359%2C64362"
//        params["_timestemp"] =  System.currentTimeMillis().toString()
        // 请求网络
        return MainBusiness.requestHome { MainService.requestHome() }
    }
}