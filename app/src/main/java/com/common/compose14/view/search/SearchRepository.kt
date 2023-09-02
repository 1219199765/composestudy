package com.common.compose14.view.search

import com.common.compose14.App
import com.common.compose14.common.room.AppDB
import com.common.compose14.common.room.Search
import com.common.compose14.common.room.SearchDao

object SearchRepository {
    private var searchDao: SearchDao = App._context?.let { AppDB.getInstance(it)?.getSearchDao() }!!

    fun requestAllRecords(): MutableList<Search> {
        return searchDao.queryAll()
    }

    suspend fun requestAddRecords(search: Search) {
        searchDao.insert(search)
    }

    suspend fun requestDeleteAll() {
        searchDao.deleteAll()
    }
}