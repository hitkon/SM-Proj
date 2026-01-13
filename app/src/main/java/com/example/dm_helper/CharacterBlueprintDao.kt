package com.example.dm_helper

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the CharacterBlueprint entity.
 */
@Dao
interface CharacterBlueprintDao {
    @Insert
    suspend fun insert(characterBlueprint: CharacterBlueprint)

    @Query("SELECT * FROM character_blueprints")
    fun getAllBlueprints(): Flow<List<CharacterBlueprint>>

    @Query("SELECT * FROM character_blueprints WHERE id = :id")
    suspend fun getBlueprintById(id: Int): CharacterBlueprint?

    @Delete
    suspend fun delete(characterBlueprint: CharacterBlueprint)

    @Update
    suspend fun update(characterBlueprint: CharacterBlueprint)
}
