package com.example.belttools.data

import com.example.belttools.data.model.MainRoomDatabase
import com.example.belttools.data.model.entities.Department
import com.example.belttools.data.model.entities.SKU
import kotlinx.coroutines.flow.Flow

class Repository(private val db: MainRoomDatabase) {

    val allDepartments: Flow<List<Department>> = db.departmentDao().getSortedDepartments()
    val allSKUs: Flow<List<SKU>> = db.sKUsDao().getSortedSKUs()

    
}