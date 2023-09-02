package com.common.compose14.common.room

import android.content.Context
import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper

/**
 * 创建数据库
 * */

@Database(entities = [Search::class], version = 1)
 abstract class AppDB:RoomDatabase() {
//    override fun clearAllTables() {
//    }
//
//    override fun createInvalidationTracker(): InvalidationTracker {
//    }
//
//    override fun createOpenHelper(config: DatabaseConfiguration): SupportSQLiteOpenHelper {
//    }


    abstract fun getSearchDao(): SearchDao

    companion object {
        @Volatile
        private var instantce: AppDB? = null
        private const val DB_NAME = "jetpack_room.db"

        fun getInstance(context: Context): AppDB? {
            if (instantce == null) {
                synchronized(AppDB::class.java) {
                    if (instantce == null) {
                        instantce = createInstance(context)
                    }
                }
            }
            return instantce
        }

        private fun createInstance(context: Context): AppDB {
            return Room.databaseBuilder(context.applicationContext, AppDB::class.java, DB_NAME)
                .allowMainThreadQueries()
                .build()
        }
    }


}