package com.example.dm_helper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class CharacterLibraryActivity : AppCompatActivity() {

    private lateinit var characterBlueprintDao: CharacterBlueprintDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_library)

        characterBlueprintDao = AppDatabase.getDatabase(this).characterBlueprintDao()

        val characterLibraryRecyclerView: RecyclerView = findViewById(R.id.character_library_recycler_view)
        val adapter = CharacterBlueprintAdapter(emptyList()) { blueprint ->
            deleteBlueprint(blueprint)
        }
        characterLibraryRecyclerView.adapter = adapter
        characterLibraryRecyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            characterBlueprintDao.getAllBlueprints().collect {
                adapter.updateBlueprints(it)
            }
        }
    }

    private fun deleteBlueprint(blueprint: CharacterBlueprint) {
        lifecycleScope.launch {
            characterBlueprintDao.delete(blueprint)
        }
    }
}
