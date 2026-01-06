package com.example.dm_helper

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var characterBlueprintDao: CharacterBlueprintDao
    private lateinit var pdfParser: PdfCharacterParser

    private val openPdfLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            it.data?.data?.let { uri ->
                lifecycleScope.launch {
                    val blueprint = pdfParser.parseCharacterBlueprint(uri)
                    blueprint?.let { characterBlueprintDao.insert(it) }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        characterBlueprintDao = AppDatabase.getDatabase(this).characterBlueprintDao()
        pdfParser = PdfCharacterParser(this)

        findViewById<Button>(R.id.btnNewGame).setOnClickListener {
            startActivity(Intent(this, SessionActivity::class.java))
        }

        findViewById<Button>(R.id.btnUploadCharacterPdf).setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "application/pdf"
            }
            openPdfLauncher.launch(intent)
        }

        findViewById<Button>(R.id.btnParseTestPdf).setOnClickListener {
            lifecycleScope.launch {
                val blueprint = pdfParser.parseCharacterBlueprintFromRaw(R.raw.argono)
                blueprint?.let { characterBlueprintDao.insert(it) }
            }
        }

        findViewById<Button>(R.id.btnCharacterLibrary).setOnClickListener {
            startActivity(Intent(this, CharacterLibraryActivity::class.java))
        }
    }
}
