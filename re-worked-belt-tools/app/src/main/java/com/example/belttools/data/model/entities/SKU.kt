package com.example.belttools.data.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sku_table")
data class SKU (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long = 0,
    @ColumnInfo(name = "notes")
    var notes: String = "",
    @ColumnInfo(name = "pallet")
    var pallet: Boolean = false,
    @ColumnInfo(name = "floor")
    var floor: Boolean = false
)