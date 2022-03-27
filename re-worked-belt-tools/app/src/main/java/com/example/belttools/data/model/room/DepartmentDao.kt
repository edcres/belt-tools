package com.example.belttools.data.model.room

import androidx.room.*
import com.example.belttools.data.model.entities.Department
import kotlinx.coroutines.flow.Flow

@Dao
interface DepartmentDao {

    @Query("SELECT * FROM department_table ORDER BY name ASC")
    fun getSortedDepartments(): Flow<List<Department>>

    @Delete
    suspend fun delete(department: Department)

    @Update
    suspend fun update(department: Department)

    @Insert
    suspend fun insert(department: Department)
}