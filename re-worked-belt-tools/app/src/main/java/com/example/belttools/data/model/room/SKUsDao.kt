package com.example.belttools.data.model.room

import androidx.room.*
import com.example.belttools.data.model.entities.Department
import com.example.belttools.data.model.entities.SKU
import kotlinx.coroutines.flow.Flow

@Dao
interface SKUsDao {

    @Query("SELECT * FROM sku_table ORDER BY id ASC")
    fun getSortedSKUs(): Flow<List<SKU>>

    @Delete
    suspend fun delete(sku: SKU)

    @Update
    suspend fun update(sku: SKU)

    @Insert
    suspend fun insert(sku: SKU)
}