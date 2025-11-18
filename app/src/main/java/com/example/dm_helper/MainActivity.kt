package com.example.dm_helper

import android.content.Intent
import android.net.Uri
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.dm_helper.data.Portrait
import com.example.dm_helper.data.PortraitDatabase
import com.example.dm_helper.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val db by lazy { PortraitDatabase.getDatabase(this) }

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            savePortraitToDatabase(uri.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNewGame.setOnClickListener {  }

        binding.btnLoadGame.setOnClickListener {  }

        binding.btnUploadPortrait.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        binding.btnShowPortraits.setOnClickListener {
            startActivity(Intent(this, PortraitGalleryActivity::class.java))
        }
    }

    private fun savePortraitToDatabase(uri: String) {
        CoroutineScope(Dispatchers.IO).launch {
            db.portraitDao().insert(Portrait(imageUri = uri))
        }

        val startSessionButton: Button = findViewById(R.id.start_session_button)
        startSessionButton.setOnClickListener {
            val intent = Intent(this, SessionActivity::class.java)
            startActivity(intent)
        }
    }
}