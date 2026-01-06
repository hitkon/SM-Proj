package com.example.dm_helper

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Character::class, CharacterBlueprint::class], version = 2) // Incremented version number
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun characterBlueprintDao(): CharacterBlueprintDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "dm_helper_database"
                )
                .fallbackToDestructiveMigration() // Add this to handle the schema change
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}