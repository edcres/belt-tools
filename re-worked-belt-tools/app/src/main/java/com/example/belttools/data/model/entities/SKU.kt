package com.example.belttools.data.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sku_table")
data class SKU (
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Long = 0,
    @ColumnInfo(name = "notes")
    var notes: String = ""
)