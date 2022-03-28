package com.example.belttools.data.model.room

import androidx.room.*
import com.example.belttools.data.model.entities.Department
import com.example.belttools.data.model.entities.SpecialtyOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface SpecialtyOrderDao {

    @Query("SELECT * FROM specialty_order_table ORDER BY id DESC")
    fun getSortedOrders(): Flow<List<SpecialtyOrder>>

    @Delete
    suspend fun delete(specialtyOrder: SpecialtyOrder)

    @Update
    suspend fun update(specialtyOrder: SpecialtyOrder)

    @Insert
    suspend fun insert(specialtyOrder: SpecialtyOrder)
}