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
    suspend fun insertDepartment(department: Department) {
        db.departmentDao().insert(department)
    }

    @WorkerThread
    suspend fun updateDepartment(department: Department) {
        db.departmentDao().update(department)
    }

    @WorkerThread
    suspend fun deleteSKU(sku: SKU) {
        db.sKUsDao().delete(sku)
    }

    @WorkerThread
    suspend fun insertSKU(sku: SKU) {
        db.sKUsDao().insert(sku)
    }

    @WorkerThread
    suspend fun updateSKU(sku: SKU) {
        db.sKUsDao().update(sku)
    }

    @WorkerThread
    suspend fun updateSKUPallet(skuId: Long) {
        db.sKUsDao().updateSKUPallet(skuId)
    }

    @WorkerThread
    suspend fun updateSKUFloor(skuId: Long) {
        db.sKUsDao().updateSKUFloor(skuId)
    }

    @WorkerThread
    suspend fun getSKUsOfPallet(): List<SKU> {
        // todo: get sku where pallet = true
        return db.sKUsDao().getPalletSKUs()
    }

    @WorkerThread
    suspend fun getSKUsOfFloor(): List<SKU> {
        // todo: get sku where floor = true
        return db.sKUsDao().getFloorSKUs()
    }
}