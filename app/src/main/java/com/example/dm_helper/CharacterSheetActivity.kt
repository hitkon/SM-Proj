package com.example.dm_helper

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
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
        findViewById<ImageView>(R.id.character_portrait_sheet).setImageResource(character.portrait)

        val hpText = findViewById<TextView>(R.id.hp_sheet)
        val minusButton = findViewById<Button>(R.id.hp_minus_button)
        val plusButton = findViewById<Button>(R.id.hp_plus_button)

        hpText.text = "HP: ${character.currentHP}/${character.maximumHP}"

        minusButton.setOnClickListener {
            showEditHpDialog(character, isIncrease = false)
        }

        plusButton.setOnClickListener {
            showEditHpDialog(character, isIncrease = true)
        }

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
        val conditions = ConditionHelper.getConditions(character)
        for (condition in conditions) {
            conditionsFlexbox.addView(createConditionView(condition, character))
        }
    }

    private fun showEditHpDialog(character: Character, isIncrease: Boolean) {
        val input = EditText(this).apply {
            inputType = android.text.InputType.TYPE_CLASS_NUMBER
            hint = "Amount"
        }

        AlertDialog.Builder(this)
            .setTitle(if (isIncrease) "Increase HP" else "Decrease HP")
            .setMessage("Enter amount")
            .setView(input)
            .setPositiveButton("OK") { _, _ ->
                val value = input.text.toString().toIntOrNull() ?: return@setPositiveButton
                lifecycleScope.launch {
                    val newHp = if (isIncrease) {
                        (character.currentHP + value)
                            .coerceAtMost(character.maximumHP)
                    } else {
                        (character.currentHP - value)
                            .coerceAtLeast(0)
                    }

                    characterDao.updateCurrentHp(character.id, newHp)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun createConditionView(condition: ConditionUi, character: Character): FrameLayout {
        val size = 96

        val container = FrameLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(size, size).apply {
                marginEnd = 16
            }
            setOnClickListener {
                onConditionTapped(condition, character)
            }
        }

        val icon = ImageView(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            setImageResource(condition.iconRes)
        }

        container.addView(icon)

        if (condition.value > 0) {
            val badge = TextView(this).apply {
                layoutParams = FrameLayout.LayoutParams(36, 36).apply {
                    gravity = android.view.Gravity.BOTTOM or android.view.Gravity.END
                }
                background = getDrawable(R.drawable.condition_badge)
                text = condition.value.toString()
                setTextColor(android.graphics.Color.WHITE)
                textSize = 12f
                gravity = android.view.Gravity.CENTER
                setTypeface(null, android.graphics.Typeface.BOLD)
            }
            container.addView(badge)
        }

        return container
    }

    private fun onConditionTapped(condition: ConditionUi, character: Character) {
        lifecycleScope.launch {
            when (condition.type) {

                ConditionValueType.INT -> {
                    val newValue = (condition.value - 1).coerceAtLeast(0)
                    when (condition.field) {
                        ConditionField.CLUMSY ->
                            characterDao.updateClumsy(character.id, newValue)

                        ConditionField.DOOMED ->
                            characterDao.updateDoomed(character.id, newValue)

                        ConditionField.DRAINED ->
                            characterDao.updateDrained(character.id, newValue)

                        ConditionField.DYING ->
                            characterDao.updateDying(character.id, newValue)

                        ConditionField.ENFEEBLED ->
                            characterDao.updateEnfeebled(character.id, newValue)

                        ConditionField.FRIGHTENED ->
                            characterDao.updateFrightened(character.id, newValue)

                        ConditionField.SICKENED ->
                            characterDao.updateSickened(character.id, newValue)

                        ConditionField.SLOWED ->
                            characterDao.updateSlowed(character.id, newValue)

                        ConditionField.STUNNED ->
                            characterDao.updateStunned(character.id, newValue)

                        ConditionField.STUPEFIED ->
                            characterDao.updateStupefied(character.id, newValue)

                        ConditionField.WOUNDED ->
                            characterDao.updateWounded(character.id, newValue)

                        else -> { }
                    }
                }

                ConditionValueType.BOOLEAN ->  {
                    when (condition.field) {
                        ConditionField.BLINDED ->
                            characterDao.updateBlinded(character.id, false)

                        ConditionField.CONCEALED ->
                            characterDao.updateConcealed(character.id, false)

                        ConditionField.CONFUSED ->
                            characterDao.updateConfused(character.id, false)

                        ConditionField.CONTROLLED ->
                            characterDao.updateControlled(character.id, false)

                        ConditionField.DAZZLED ->
                            characterDao.updateDazzled(character.id, false)

                        ConditionField.DEAFENED ->
                            characterDao.updateDeafened(character.id, false)

                        ConditionField.ENCUMBERED ->
                            characterDao.updateEncumbered(character.id, false)

                        ConditionField.FASCINATED ->
                            characterDao.updateFascinated(character.id, false)

                        ConditionField.FATIGUED ->
                            characterDao.updateFatigued(character.id, false)

                        ConditionField.FLAT_FOOTED ->
                            characterDao.updateFlatFooted(character.id, false)

                        ConditionField.FLEEING ->
                            characterDao.updateFleeing(character.id, false)

                        ConditionField.GRABBED ->
                            characterDao.updateGrabbed(character.id, false)

                        ConditionField.IMMOBILIZED ->
                            characterDao.updateImmobilized(character.id, false)

                        ConditionField.INVISIBLE ->
                            characterDao.updateInvisible(character.id, false)

                        ConditionField.PARALYZED ->
                            characterDao.updateParalyzed(character.id, false)

                        ConditionField.PETRIFIED ->
                            characterDao.updatePetrified(character.id, false)

                        ConditionField.PRONE ->
                            characterDao.updateProne(character.id, false)

                        ConditionField.QUICKENED ->
                            characterDao.updateQuickened(character.id, false)

                        ConditionField.RESTRAINED ->
                            characterDao.updateRestrained(character.id, false)

                        ConditionField.UNCONSCIOUS ->
                            characterDao.updateUnconscious(character.id, false)

                        else -> { }
                    }
                }
            }
        }
    }
}
