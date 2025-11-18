package com.example.dm_helper.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PortraitDao {
    @Insert
    suspend fun insert(portrait: Portrait)

    @Query("SELECT * FROM portraits")
    suspend fun getAll(): List<Portrait>
}