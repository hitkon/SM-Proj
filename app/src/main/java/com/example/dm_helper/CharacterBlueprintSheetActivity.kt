package com.example.dm_helper

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.flexbox.FlexboxLayout
import kotlinx.coroutines.launch
import kotlin.math.floor

class CharacterBlueprintSheetActivity : AppCompatActivity() {

    companion object {
        const val BLUEPRINT_ID = "BLUEPRINT_ID"
    }

    private lateinit var characterBlueprintDao: CharacterBlueprintDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.character_sheet)

        characterBlueprintDao = AppDatabase.getDatabase(this).characterBlueprintDao()

        val blueprintId = intent.getIntExtra(BLUEPRINT_ID, -1)
        if (blueprintId != -1) {
            lifecycleScope.launch {
                characterBlueprintDao.getBlueprintById(blueprintId).collect { blueprint ->
                    blueprint?.let { populateCharacterSheet(it) }
                }
            }
        }
    }

    private fun populateCharacterSheet(blueprint: CharacterBlueprint) {
        findViewById<TextView>(R.id.character_name_sheet).text = blueprint.name
        // Portrait would be loaded here with Glide/Coil if imageUrl is present
        // findViewById<ImageView>(R.id.character_portrait_sheet).setImageResource(blueprint.portrait)

        findViewById<TextView>(R.id.hp_sheet).text = "HP: ${blueprint.maximumHP}/${blueprint.maximumHP}"
        findViewById<TextView>(R.id.ac_sheet).text = "AC: ${blueprint.ac}"
        findViewById<TextView>(R.id.speed_sheet).text = "Speed: ${blueprint.speed}"

        fun formatModifier(value: Int): String {
            val modifier = floor((value - 10) / 2.0).toInt()
            return if (modifier >= 0) "+${modifier}" else modifier.toString()
        }

        findViewById<TextView>(R.id.str_sheet).text = "STR\n${formatModifier(blueprint.str)}"
        findViewById<TextView>(R.id.dex_sheet).text = "DEX\n${formatModifier(blueprint.dex)}"
        findViewById<TextView>(R.id.con_sheet).text = "CON\n${formatModifier(blueprint.con)}"
        findViewById<TextView>(R.id.int_sheet).text = "INT\n${formatModifier(blueprint.int)}"
        findViewById<TextView>(R.id.wis_sheet).text = "WIS\n${formatModifier(blueprint.wis)}"
        findViewById<TextView>(R.id.cha_sheet).text = "CHA\n${formatModifier(blueprint.cha)}"

        findViewById<TextView>(R.id.fortitude_sheet).text = "Fortitude\n+${blueprint.fortitude}"
        findViewById<TextView>(R.id.reflex_sheet).text = "Reflex\n+${blueprint.reflex}"
        findViewById<TextView>(R.id.will_sheet).text = "Will\n+${blueprint.will}"

        findViewById<TextView>(R.id.acrobatics_sheet).text = "Acrobatics: ${blueprint.acrobatics}"
        findViewById<TextView>(R.id.arcana_sheet).text = "Arcana: ${blueprint.arcana}"
        findViewById<TextView>(R.id.athletics_sheet).text = "Athletics: ${blueprint.athletics}"
        findViewById<TextView>(R.id.crafting_sheet).text = "Crafting: ${blueprint.crafting}"
        findViewById<TextView>(R.id.deception_sheet).text = "Deception: ${blueprint.deception}"
        findViewById<TextView>(R.id.diplomacy_sheet).text = "Diplomacy: ${blueprint.diplomacy}"
        findViewById<TextView>(R.id.intimidation_sheet).text = "Intimidation: ${blueprint.intimidation}"
        findViewById<TextView>(R.id.medicine_sheet).text = "Medicine: ${blueprint.medicine}"
        findViewById<TextView>(R.id.nature_sheet).text = "Nature: ${blueprint.nature}"
        findViewById<TextView>(R.id.occultism_sheet).text = "Occultism: ${blueprint.occultism}"
        findViewById<TextView>(R.id.performance_sheet).text = "Performance: ${blueprint.performance}"
        findViewById<TextView>(R.id.religion_sheet).text = "Religion: ${blueprint.religion}"
        findViewById<TextView>(R.id.society_sheet).text = "Society: ${blueprint.society}"
        findViewById<TextView>(R.id.stealth_sheet).text = "Stealth: ${blueprint.stealth}"
        findViewById<TextView>(R.id.survival_sheet).text = "Survival: ${blueprint.survival}"
        findViewById<TextView>(R.id.thievery_sheet).text = "Thievery: ${blueprint.thievery}"

        findViewById<TextView>(R.id.resistances_sheet).text = blueprint.resistances.joinToString { "${it.first} ${it.second}" }
        findViewById<TextView>(R.id.weaknesses_sheet).text = blueprint.weaknesses.joinToString { "${it.first} ${it.second}" }
        findViewById<TextView>(R.id.immunities_sheet).text = blueprint.immunities.joinToString()
        findViewById<TextView>(R.id.traits_sheet).text = blueprint.traits.joinToString()

        // Since this is a blueprint, there are no active conditions to display.
        findViewById<FlexboxLayout>(R.id.conditions_flexbox).visibility = View.GONE
    }
}