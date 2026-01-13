package com.example.dm_helper

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Insert
    suspend fun insert(character: Character)

    @Query("SELECT * FROM characters")
    fun getAllCharacters(): Flow<List<Character>>

    @Query("SELECT * FROM characters WHERE id = :characterId")
    fun getCharacterById(characterId: Int): Flow<Character?>
    
    @Query("SELECT * FROM characters WHERE id = :id")
    suspend fun getCharacterByIdSuspend(id: Int): Character?

    @Delete
    suspend fun delete(character: Character)

    @Update
    suspend fun update(character: Character)

    @Query("UPDATE characters SET currentHP = :hp WHERE id = :id")
    suspend fun updateCurrentHp(id: Int, hp: Int)

    // Integer-based conditions
    @Query("UPDATE characters SET clumsy = :value WHERE id = :id")
    suspend fun updateClumsy(id: Int, value: Int)

    @Query("UPDATE characters SET doomed = :value WHERE id = :id")
    suspend fun updateDoomed(id: Int, value: Int)

    @Query("UPDATE characters SET drained = :value WHERE id = :id")
    suspend fun updateDrained(id: Int, value: Int)

    @Query("UPDATE characters SET dying = :value WHERE id = :id")
    suspend fun updateDying(id: Int, value: Int)

    @Query("UPDATE characters SET enfeebled = :value WHERE id = :id")
    suspend fun updateEnfeebled(id: Int, value: Int)

    @Query("UPDATE characters SET frightened = :value WHERE id = :id")
    suspend fun updateFrightened(id: Int, value: Int)

    @Query("UPDATE characters SET sickened = :value WHERE id = :id")
    suspend fun updateSickened(id: Int, value: Int)

    @Query("UPDATE characters SET slowed = :value WHERE id = :id")
    suspend fun updateSlowed(id: Int, value: Int)

    @Query("UPDATE characters SET stunned = :value WHERE id = :id")
    suspend fun updateStunned(id: Int, value: Int)

    @Query("UPDATE characters SET stupefied = :value WHERE id = :id")
    suspend fun updateStupefied(id: Int, value: Int)

    @Query("UPDATE characters SET wounded = :value WHERE id = :id")
    suspend fun updateWounded(id: Int, value: Int)

    // Boolean-based conditions
    @Query("UPDATE characters SET blinded = :value WHERE id = :id")
    suspend fun updateBlinded(id: Int, value: Boolean)

    @Query("UPDATE characters SET concealed = :value WHERE id = :id")
    suspend fun updateConcealed(id: Int, value: Boolean)

    @Query("UPDATE characters SET confused = :value WHERE id = :id")
    suspend fun updateConfused(id: Int, value: Boolean)

    @Query("UPDATE characters SET controlled = :value WHERE id = :id")
    suspend fun updateControlled(id: Int, value: Boolean)

    @Query("UPDATE characters SET dazzled = :value WHERE id = :id")
    suspend fun updateDazzled(id: Int, value: Boolean)

    @Query("UPDATE characters SET deafened = :value WHERE id = :id")
    suspend fun updateDeafened(id: Int, value: Boolean)

    @Query("UPDATE characters SET encumbered = :value WHERE id = :id")
    suspend fun updateEncumbered(id: Int, value: Boolean)

    @Query("UPDATE characters SET fascinated = :value WHERE id = :id")
    suspend fun updateFascinated(id: Int, value: Boolean)

    @Query("UPDATE characters SET fatigued = :value WHERE id = :id")
    suspend fun updateFatigued(id: Int, value: Boolean)

    @Query("UPDATE characters SET flatFooted = :value WHERE id = :id")
    suspend fun updateFlatFooted(id: Int, value: Boolean)

    @Query("UPDATE characters SET fleeing = :value WHERE id = :id")
    suspend fun updateFleeing(id: Int, value: Boolean)

    @Query("UPDATE characters SET grabbed = :value WHERE id = :id")
    suspend fun updateGrabbed(id: Int, value: Boolean)

    @Query("UPDATE characters SET immobilized = :value WHERE id = :id")
    suspend fun updateImmobilized(id: Int, value: Boolean)

    @Query("UPDATE characters SET invisible = :value WHERE id = :id")
    suspend fun updateInvisible(id: Int, value: Boolean)

    @Query("UPDATE characters SET paralyzed = :value WHERE id = :id")
    suspend fun updateParalyzed(id: Int, value: Boolean)

    @Query("UPDATE characters SET petrified = :value WHERE id = :id")
    suspend fun updatePetrified(id: Int, value: Boolean)

    @Query("UPDATE characters SET prone = :value WHERE id = :id")
    suspend fun updateProne(id: Int, value: Boolean)

    @Query("UPDATE characters SET quickened = :value WHERE id = :id")
    suspend fun updateQuickened(id: Int, value: Boolean)

    @Query("UPDATE characters SET restrained = :value WHERE id = :id")
    suspend fun updateRestrained(id: Int, value: Boolean)

    @Query("UPDATE characters SET unconscious = :value WHERE id = :id")
    suspend fun updateUnconscious(id: Int, value: Boolean)
}
