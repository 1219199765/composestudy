package com.common.compose14.common.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


//创建表名

/**
 * @Entity注解是标识实体类，并通过tableName指定该实体类指向的表名，
 * @PrimaryKey注解标识主键，这个跟greenDao这些数据库基本是一样的，
 * autoGenerate = true则表示主键自增，
 * @ColumnInfo注解标识列参数的信息，records =是指定数据库字段名称，不指定默认是自定义的值。
 *
 * */
@Entity(tableName = "search")
data class Search(
    @ColumnInfo var records: String?=null,
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    )
