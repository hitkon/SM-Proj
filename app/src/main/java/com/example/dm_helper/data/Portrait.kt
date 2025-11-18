package com.example.dm_helper.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "portraits")
data class Portrait(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val imageUri: String
)
