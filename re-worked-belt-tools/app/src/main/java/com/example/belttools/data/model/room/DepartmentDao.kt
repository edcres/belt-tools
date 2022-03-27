package com.example.belttools.data.model.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.belttools.data.model.entities.Department
import kotlinx.coroutines.flow.Flow

@Dao
interface DepartmentDao {

    @Query("SELECT * FROM department_table ORDER BY name ASC")
    fun getSortedDepartments(): Flow<List<Department>>

    @Delete
    suspend fun delete(department: Department)

    @Insert
    suspend fun insert(department: Department)
}