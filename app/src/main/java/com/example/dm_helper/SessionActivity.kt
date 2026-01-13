package com.example.dm_helper

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class SessionActivity : AppCompatActivity() {

    private lateinit var characterDao: CharacterDao
    private lateinit var characterBlueprintDao: CharacterBlueprintDao

    private val getBlueprintLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val blueprintId = it.data?.getIntExtra(CharacterLibraryActivity.EXTRA_BLUEPRINT_ID, -1) ?: -1
            if (blueprintId != -1) {
                lifecycleScope.launch {
                    val blueprint = characterBlueprintDao.getBlueprintById(blueprintId)
                    blueprint?.let {
                        val character = convertBlueprintToCharacter(it)
                        characterDao.insert(character)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.session_layout)

        characterDao = AppDatabase.getDatabase(this).characterDao()
        characterBlueprintDao = AppDatabase.getDatabase(this).characterBlueprintDao()

        val initiativeRecyclerView: RecyclerView = findViewById(R.id.initiative_recycler_view)
        val adapter = InitiativeAdapter(
            mutableListOf(),
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                    (recyclerView.adapter as InitiativeAdapter).moveItem(viewHolder.adapterPosition, target.adapterPosition)
                    return true
                }
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
            }),
            onDeleteCharacter = { character ->
                lifecycleScope.launch { characterDao.delete(character) }
            }
        )
        initiativeRecyclerView.adapter = adapter
        initiativeRecyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            characterDao.getAllCharacters().collect {
                adapter.updateCharacters(it)
            }
        }

        findViewById<FloatingActionButton>(R.id.add_character_fab).setOnClickListener {
            showAddCharacterDialog()
        }
    }

    private fun showAddCharacterDialog() {
        val options = arrayOf("Add Test Goblin", "Add from Library")
        AlertDialog.Builder(this)
            .setTitle("Add Character")
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> addTestGoblin()
                    1 -> {
                        val intent = Intent(this, CharacterLibraryActivity::class.java).apply{
                            action = Intent.ACTION_PICK
                        }
                        getBlueprintLauncher.launch(intent)
                    }
                }
            }
            .show()
    }

    private fun addTestGoblin() {
        lifecycleScope.launch {
            characterDao.insert(createTestCharacter())
        }
    }

    private fun convertBlueprintToCharacter(blueprint: CharacterBlueprint): Character {
        return Character(
            name = blueprint.name,
            initiative = 0,
            portrait = blueprint.portrait,
            imageUrl = blueprint.imageUrl,
            conditions = emptyList(),
            currentHP = blueprint.maximumHP,
            maximumHP = blueprint.maximumHP,
            tempHP = 0,
            perception = blueprint.perception,
            speed = blueprint.speed,
            heroPoints = blueprint.heroPoints,
            str = blueprint.str, dex = blueprint.dex, con = blueprint.con, wis = blueprint.wis, `int` = blueprint.int, cha = blueprint.cha,
            reflex = blueprint.reflex, fortitude = blueprint.fortitude, will = blueprint.will, ac = blueprint.ac,
            bmUsedFrom = emptyList(),
            acrobatics = blueprint.acrobatics, arcana = blueprint.arcana, athletics = blueprint.athletics, crafting = blueprint.crafting, deception = blueprint.deception,
            diplomacy = blueprint.diplomacy, intimidation = blueprint.intimidation, medicine = blueprint.medicine, nature = blueprint.nature,
            occultism = blueprint.occultism, performance = blueprint.performance, religion = blueprint.religion, society = blueprint.society,
            stealth = blueprint.stealth, survival = blueprint.survival, thievery = blueprint.thievery,
            resistances = blueprint.resistances, weaknesses = blueprint.weaknesses, traits = blueprint.traits, immunities = blueprint.immunities,
            blinded = false, clumsy = 0, concealed = false, confused = false, controlled = false, dazzled = false, deafened = false,
            doomed = 0, drained = 0, dying = 0, encumbered = false, enfeebled = 0, fascinated = false, fatigued = false, flatFooted = false,
            fleeing = false, frightened = 0, grabbed = false, immobilized = false, invisible = false, paralyzed = false,
            persistentDamage = emptyList(), petrified = false, prone = false, quickened = false, restrained = false, sickened = 0,
            slowed = 0, stunned = 0, stupefied = 0, unconscious = false, wounded = 0
        )
    }
}