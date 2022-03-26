package com.example.belttools.data.model

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room
import com.example.belttools.data.model.entities.Department
import com.example.belttools.data.model.entities.SKU
import com.example.belttools.data.model.entities.SpecialtyOrder
import com.example.belttools.data.model.room.DepartmentDao
import com.example.belttools.data.model.room.SKUsDao
import com.example.belttools.data.model.room.SpecialtyOrderDao

@Database(
    entities = [SpecialtyOrder::class, Department::class, SKU::class],
    version = 2,
    exportSchema = false
)
abstract class MainRoomDatabase : RoomDatabase() {

    abstract fun specialtyOrderDao(): SpecialtyOrderDao
    abstract fun departmentDao(): DepartmentDao
    abstract fun sKUsDao(): SKUsDao

    companion object {
        @Volatile
        private var INSTANCE: MainRoomDatabase? = null
        private const val DATABASE_NAME = "main_database"

        fun getInstance(context: Context): MainRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    MainRoomDatabase::class.java,
                    DATABASE_NAME
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}