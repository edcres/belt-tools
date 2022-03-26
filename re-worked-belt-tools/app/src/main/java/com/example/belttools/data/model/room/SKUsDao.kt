package com.example.belttools.data.model.room

import androidx.room.Dao
import androidx.room.Query
import com.example.belttools.data.model.entities.SKU
import kotlinx.coroutines.flow.Flow

@Dao
interface SKUsDao {
    @Query("SELECT * FROM sku_table ORDER BY id ASC")
    fun getSortedSKUs(): Flow<List<SKU>>
}