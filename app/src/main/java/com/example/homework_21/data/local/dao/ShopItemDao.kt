package com.example.homework_21.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.homework_21.data.local.entity.ShopItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShopItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item : ShopItemEntity)

    @Query("SELECT * FROM saved_items")
    fun getAllItems(): Flow<List<ShopItemEntity>>
}