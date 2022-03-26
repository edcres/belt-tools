package com.example.belttools.data.model.room

import androidx.room.Dao
import androidx.room.Query
import com.example.belttools.data.model.entities.Pallet
import kotlinx.coroutines.flow.Flow

@Dao
interface PalletDao {
    @Query("SELECT * FROM pallet_table ORDER BY id ASC")
    fun getSortedPallets(): Flow<List<Pallet>>
}