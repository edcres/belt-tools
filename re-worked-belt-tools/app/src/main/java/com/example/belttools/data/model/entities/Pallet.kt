package com.example.belttools.data.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pallet_table")
data class Pallet (
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Long = 0,
    @ColumnInfo(name = "notes")
    var notes: String = ""
)