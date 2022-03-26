package com.example.belttools.data.model.room

import androidx.room.Dao
import androidx.room.Query
import com.example.belttools.data.model.entities.Department
import kotlinx.coroutines.flow.Flow

@Dao
interface DepartmentDao {
    @Query("SELECT * FROM department_table ORDER BY id ASC")
    fun getSortedDepartments(): Flow<List<Department>>
}