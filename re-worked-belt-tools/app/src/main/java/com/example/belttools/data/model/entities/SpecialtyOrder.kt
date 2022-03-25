package com.example.belttools.data.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "specialty_order_table")
data class SpecialtyOrder (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,
    @ColumnInfo(name = "order_num")
    var orderNum: Long = 0,
    @ColumnInfo(name = "info")
    var info: Long = 0,
    @ColumnInfo(name = "note")
    var note: Long = 0
)