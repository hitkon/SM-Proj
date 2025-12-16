package com.example.dm_helper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class SessionActivity : AppCompatActivity() {
    private lateinit var characterDao: CharacterDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.session_layout)

        characterDao = AppDatabase.getDatabase(this).characterDao()

        val initiativeRecyclerView: RecyclerView = findViewById(R.id.initiative_recycler_view)
        initiativeRecyclerView.layoutManager = LinearLayoutManager(this)

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                (recyclerView.adapter as InitiativeAdapter).moveItem(
                    viewHolder.adapterPosition,
                    target.adapterPosition
                )
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
        })

        val adapter = InitiativeAdapter(mutableListOf(), itemTouchHelper) { character ->
            deleteCharacter(character)
        }
        initiativeRecyclerView.adapter = adapter
        itemTouchHelper.attachToRecyclerView(initiativeRecyclerView)

        lifecycleScope.launch {
            characterDao.getAllCharacters().collect {
                adapter.updateCharacters(it)
            }
        }

        val fab: FloatingActionButton = findViewById(R.id.add_character_fab)
        fab.setOnClickListener {
            lifecycleScope.launch {
                characterDao.insert(createTestCharacter())
            }
        }
    }

    private fun deleteCharacter(character: Character) {
        lifecycleScope.launch {
            characterDao.delete(character)
        }
    }
}