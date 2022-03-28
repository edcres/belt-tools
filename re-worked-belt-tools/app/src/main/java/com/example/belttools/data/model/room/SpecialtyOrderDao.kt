package com.example.belttools.data.model.room

import androidx.room.Dao
import androidx.room.Query
import com.example.belttools.data.model.entities.SpecialtyOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface SpecialtyOrderDao {

    @Query("SELECT * FROM specialty_order_table ORDER BY id DESC")
    fun getSortedOrders(): Flow<List<SpecialtyOrder>>
}