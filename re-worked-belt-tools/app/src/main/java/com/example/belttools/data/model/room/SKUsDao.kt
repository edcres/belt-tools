package com.example.belttools.data.model.room

import androidx.room.*
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

    @Query(
        "SELECT * FROM sku_table " +
                "WHERE is_of_pallet = :pallet " +
                "ORDER BY id ASC"
    )
    fun getPalletSKUs(pallet: Boolean = true): List<SKU>

    @Query(
        "SELECT * FROM sku_table " +
                "WHERE is_of_floor = :floor " +
                "ORDER BY id ASC"
    )
    fun getFloorSKUs(floor: Boolean = true): List<SKU>
}