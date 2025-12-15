package com.example.dm_helper

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.flexbox.FlexboxLayout
import kotlinx.coroutines.launch
import kotlin.math.floor

class CharacterSheetActivity : AppCompatActivity() {

    companion object {
        const val CHARACTER_ID = "CHARACTER_ID"
    }

    private lateinit var characterDao: CharacterDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.character_sheet)

        characterDao = AppDatabase.getDatabase(this).characterDao()

        val characterId = intent.getIntExtra(CHARACTER_ID, -1)
        if (characterId != -1) {
            lifecycleScope.launch {
                characterDao.getCharacterById(characterId).collect { character ->
                    character?.let { populateCharacterSheet(it) }
                }
            }
        }
    }

    private fun populateCharacterSheet(character: Character) {
        findViewById<TextView>(R.id.character_name_sheet).text = character.name
        // Portrait would be loaded here with Glide/Coil if imageUrl is present
        // findViewById<ImageView>(R.id.character_portrait_sheet).setImageResource(character.portrait)

        findViewById<TextView>(R.id.hp_sheet).text = "HP: ${character.currentHP}/${character.maximumHP}"
        findViewById<TextView>(R.id.ac_sheet).text = "AC: ${character.ac}"
        findViewById<TextView>(R.id.speed_sheet).text = "Speed: ${character.speed}"

        fun formatModifier(value: Int): String {
            val modifier = floor((value - 10) / 2.0).toInt()
            return if (modifier >= 0) "+${modifier}" else modifier.toString()
        }

        findViewById<TextView>(R.id.str_sheet).text = "STR\n${formatModifier(character.str)}"
        findViewById<TextView>(R.id.dex_sheet).text = "DEX\n${formatModifier(character.dex)}"
        findViewById<TextView>(R.id.con_sheet).text = "CON\n${formatModifier(character.con)}"
        findViewById<TextView>(R.id.int_sheet).text = "INT\n${formatModifier(character.int)}"
        findViewById<TextView>(R.id.wis_sheet).text = "WIS\n${formatModifier(character.wis)}"
        findViewById<TextView>(R.id.cha_sheet).text = "CHA\n${formatModifier(character.cha)}"

        findViewById<TextView>(R.id.fortitude_sheet).text = "Fortitude\n+${character.fortitude}"
        findViewById<TextView>(R.id.reflex_sheet).text = "Reflex\n+${character.reflex}"
        findViewById<TextView>(R.id.will_sheet).text = "Will\n+${character.will}"

        findViewById<TextView>(R.id.acrobatics_sheet).text = "Acrobatics: ${character.acrobatics}"
        findViewById<TextView>(R.id.arcana_sheet).text = "Arcana: ${character.arcana}"
        findViewById<TextView>(R.id.athletics_sheet).text = "Athletics: ${character.athletics}"
        findViewById<TextView>(R.id.crafting_sheet).text = "Crafting: ${character.crafting}"
        findViewById<TextView>(R.id.deception_sheet).text = "Deception: ${character.deception}"
        findViewById<TextView>(R.id.diplomacy_sheet).text = "Diplomacy: ${character.diplomacy}"
        findViewById<TextView>(R.id.intimidation_sheet).text = "Intimidation: ${character.intimidation}"
        findViewById<TextView>(R.id.medicine_sheet).text = "Medicine: ${character.medicine}"
        findViewById<TextView>(R.id.nature_sheet).text = "Nature: ${character.nature}"
        findViewById<TextView>(R.id.occultism_sheet).text = "Occultism: ${character.occultism}"
        findViewById<TextView>(R.id.performance_sheet).text = "Performance: ${character.performance}"
        findViewById<TextView>(R.id.religion_sheet).text = "Religion: ${character.religion}"
        findViewById<TextView>(R.id.society_sheet).text = "Society: ${character.society}"
        findViewById<TextView>(R.id.stealth_sheet).text = "Stealth: ${character.stealth}"
        findViewById<TextView>(R.id.survival_sheet).text = "Survival: ${character.survival}"
        findViewById<TextView>(R.id.thievery_sheet).text = "Thievery: ${character.thievery}"

        findViewById<TextView>(R.id.resistances_sheet).text = character.resistances.joinToString { "${it.first} ${it.second}" }
        findViewById<TextView>(R.id.weaknesses_sheet).text = character.weaknesses.joinToString { "${it.first} ${it.second}" }
        findViewById<TextView>(R.id.immunities_sheet).text = character.immunities.joinToString()
        findViewById<TextView>(R.id.traits_sheet).text = character.traits.joinToString()

        // Handle conditions display...
        val conditionsFlexbox = findViewById<FlexboxLayout>(R.id.conditions_flexbox)
        conditionsFlexbox.removeAllViews()
        val conditionIcons = ConditionHelper.getConditionIcons(character)
        for (condition in conditionIcons) {
            val imageView = ImageView(this)
            imageView.setImageResource(condition)
            val layoutParams = LinearLayout.LayoutParams(96, 96)
            layoutParams.marginEnd = 16
            imageView.layoutParams = layoutParams
            conditionsFlexbox.addView(imageView)
        }
    }
}
