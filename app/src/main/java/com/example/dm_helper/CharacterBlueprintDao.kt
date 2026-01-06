package com.example.dm_helper

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
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
    fun getBlueprintById(id: Int): Flow<CharacterBlueprint?>

    @Delete
    suspend fun delete(characterBlueprint: CharacterBlueprint)
}
