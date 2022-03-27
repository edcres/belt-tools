package com.example.belttools.data

import androidx.annotation.WorkerThread
import com.example.belttools.data.model.MainRoomDatabase
import com.example.belttools.data.model.entities.Department
import com.example.belttools.data.model.entities.SKU
import kotlinx.coroutines.flow.Flow

class Repository(private val db: MainRoomDatabase) {

    val allDepartments: Flow<List<Department>> = db.departmentDao().getSortedDepartments()
    val allSKUs: Flow<List<SKU>> = db.sKUsDao().getSortedSKUs()

    @WorkerThread
    suspend fun deleteDepartment(department: Department) {
        db.departmentDao().delete(department)
    }

    @WorkerThread
    suspend fun deleteSKU(sku: SKU) {
        db.sKUsDao().delete(sku)
    }

    @WorkerThread
    suspend fun updateDepartment(department: Department) {
        db.departmentDao().update(department)
    }

    @WorkerThread
    suspend fun updateSKU(sku: SKU) {
        db.sKUsDao().update(sku)
    }

    @WorkerThread
    suspend fun insertDepartment(department: Department) {
        db.departmentDao().insert(department)
    }

    @WorkerThread
    suspend fun insertSKU(sku: SKU) {
        db.sKUsDao().insert(sku)
    }
}