package com.example.dm_helper.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Portrait::class], version = 1)
abstract class PortraitDatabase : RoomDatabase() {
    abstract fun portraitDao(): PortraitDao

    companion object {
        @Volatile private var INSTANCE: PortraitDatabase? = null

        fun getDatabase(context: Context): PortraitDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    PortraitDatabase::class.java,
                    "portrait_db"
                ).build().also { INSTANCE = it }
            }
    }
}