package com.example.dm_helper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SessionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.session_layout)

        val characters = mutableListOf(
            Character("Aragorn", 20, R.drawable.ic_launcher_background, listOf(R.drawable.ic_launcher_background), 100, 100),
            Character("Legolas", 22, R.drawable.ic_launcher_background, listOf(), 80, 80),
            Character("Gimli", 18, R.drawable.ic_launcher_background, listOf(R.drawable.ic_launcher_background, R.drawable.ic_launcher_background), 120, 120)
        ).sortedByDescending { it.initiative }.toMutableList()

        val initiativeRecyclerView: RecyclerView = findViewById(R.id.initiative_recycler_view)
        initiativeRecyclerView.layoutManager = LinearLayoutManager(this)

        val callback = object : ItemTouchHelper.SimpleCallback(
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
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        val adapter = InitiativeAdapter(characters, itemTouchHelper)
        initiativeRecyclerView.adapter = adapter
        itemTouchHelper.attachToRecyclerView(initiativeRecyclerView)
    }
}