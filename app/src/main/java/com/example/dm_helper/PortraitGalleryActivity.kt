package com.example.dm_helper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dm_helper.adapters.PortraitAdapter
import com.example.dm_helper.data.PortraitDatabase
import com.example.dm_helper.databinding.ActivityPortraitGalleryBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PortraitGalleryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPortraitGalleryBinding
    private val db by lazy { PortraitDatabase.getDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPortraitGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)

        CoroutineScope(Dispatchers.IO).launch {
            val portraits = db.portraitDao().getAll()
            runOnUiThread {
                binding.recyclerView.adapter = PortraitAdapter(portraits)
            }
        }
    }
}