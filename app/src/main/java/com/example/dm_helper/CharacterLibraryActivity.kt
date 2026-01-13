package com.example.dm_helper

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class CharacterLibraryActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_BLUEPRINT_ID = "EXTRA_BLUEPRINT_ID"
    }

    private lateinit var characterBlueprintDao: CharacterBlueprintDao
    private var blueprintToUpdateId: Int = -1

    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            it.data?.data?.let { uri ->
                val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION
                contentResolver.takePersistableUriPermission(uri, takeFlags)
                lifecycleScope.launch {
                    val blueprint = characterBlueprintDao.getBlueprintById(blueprintToUpdateId)
                    blueprint?.let {
                        val updatedBlueprint = it.copy(imageUrl = uri.toString())
                        characterBlueprintDao.update(updatedBlueprint)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_library)

        characterBlueprintDao = AppDatabase.getDatabase(this).characterBlueprintDao()

        val isForSelection = intent.action == Intent.ACTION_PICK

        val characterLibraryRecyclerView: RecyclerView = findViewById(R.id.character_library_recycler_view)
        
        val adapter = CharacterBlueprintAdapter(
            emptyList(),
            // Always provide an item click listener
            onItemClicked = { blueprint ->
                if (isForSelection) {
                    val resultIntent = Intent().apply {
                        putExtra(EXTRA_BLUEPRINT_ID, blueprint.id)
                    }
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                } else {
                    // In management mode, clicking an item opens its full sheet
                    val intent = Intent(this, CharacterBlueprintSheetActivity::class.java).apply {
                        putExtra(CharacterBlueprintSheetActivity.BLUEPRINT_ID, blueprint.id)
                    }
                    startActivity(intent)
                }
            },
            // Only provide delete and upload listeners when in management mode
            onDeleteClicked = if (!isForSelection) { blueprint -> deleteBlueprint(blueprint) } else null,
            onUploadImageClicked = if (!isForSelection) { blueprintId -> uploadImageFor(blueprintId) } else null
        )
        
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
    
    private fun uploadImageFor(blueprintId: Int) {
        blueprintToUpdateId = blueprintId
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
        selectImageLauncher.launch(intent)
    }
}