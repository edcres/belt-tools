package com.example.belttools.data.model

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room

@Database(entities = [/* todo: WorkoutGroup::class, Workout::class, WorkoutSet::class*/],
    version = 1,
    exportSchema = false)
abstract class MainRoomDatabase : RoomDatabase() {

    // todo:
//    abstract fun workoutGroupDao(): WorkoutGroupDao
//    abstract fun workoutDao(): WorkoutDao
//    abstract fun workoutSetDao(): WorkoutSetDao

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