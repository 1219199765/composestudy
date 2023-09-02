package com.common.compose14.common.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
@Dao
interface SearchDao {
    //插入多个数据
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(words: MutableList<Search>)

    //插入单个数据
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(words: Search)

    //删除表中所有数据
    @Query("DELETE FROM search")
    suspend fun deleteAll()

    //根据Id删除数据
    @Query("DELETE FROM search WHERE id=:id")
    suspend fun deleteById(id: Long)

    //根据属性值删除数据
    @Query("DELETE FROM search WHERE records=:word")
    suspend fun deleteByName(word: String)

    //通过id修改数据
    @Query("UPDATE search SET records=:word WHERE id=:id")
    suspend fun updateData(id: Long, word: String)

    //获取所有数据
    @Query("SELECT * FROM search")
    fun queryAll(): MutableList<Search>

    //根据id获取一个数据
    @Query("SELECT * FROM search WHERE id = :id")
    fun getWordById(id: Int): Search?
}