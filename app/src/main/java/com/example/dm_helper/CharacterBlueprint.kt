package com.example.dm_helper

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a character template or blueprint, typically parsed from a source like a PDF.
 * This entity stores the immutable stats of a character.
 */
@Entity(tableName = "character_blueprints")
data class CharacterBlueprint(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val portrait: Int,
    val imageUrl: String? = null,
    val maximumHP: Int,
    val perception: Int,
    val speed: Int,
    val heroPoints: Int,
    val str: Int,
    val dex: Int,
    val con: Int,
    val wis: Int,
    val `int`: Int,
    val cha: Int,
    val reflex: Int,
    val fortitude: Int,
    val will: Int,
    val ac: Int,
    val acrobatics: Int,
    val arcana: Int,
    val athletics: Int,
    val crafting: Int,
    val deception: Int,
    val diplomacy: Int,
    val intimidation: Int,
    val medicine: Int,
    val nature: Int,
    val occultism: Int,
    val performance: Int,
    val religion: Int,
    val society: Int,
    val stealth: Int,
    val survival: Int,
    val thievery: Int,
    val resistances: List<Pair<String, Int>>,
    val weaknesses: List<Pair<String, Int>>,
    val traits: List<String>,
    val immunities: List<String>
)
