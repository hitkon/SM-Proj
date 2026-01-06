package com.example.dm_helper

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CharacterBlueprintAdapter(
    private var blueprints: List<CharacterBlueprint>,
    private val onDeleteClicked: (CharacterBlueprint) -> Unit
) : RecyclerView.Adapter<CharacterBlueprintAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val characterPortrait: ImageView = view.findViewById(R.id.character_portrait)
        val hpValue: TextView = view.findViewById(R.id.hp_value)
        val removeCharacterButton: ImageButton = view.findViewById(R.id.remove_character_button)
        val characterName: TextView = view.findViewById(R.id.character_name)
        val conditionIconsLayout: LinearLayout = view.findViewById(R.id.condition_icons_layout)
        val initiativeValue: TextView = view.findViewById(R.id.initiative_value)
        val dragHandle: ImageView = view.findViewById(R.id.drag_handle)
        val characterSheetButton: ImageButton = view.findViewById(R.id.character_sheet_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.initiative_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val blueprint = blueprints[position]

        holder.characterName.text = blueprint.name
        holder.characterPortrait.setImageResource(blueprint.portrait) // Uses the default portrait for now

        // Repurpose views for the library context
        holder.hpValue.text = "${blueprint.maximumHP}/${blueprint.maximumHP}"
        holder.initiativeValue.text = "AC: ${blueprint.ac}"

        // Hide views that are not relevant for the library
        holder.dragHandle.visibility = View.GONE
        holder.conditionIconsLayout.visibility = View.GONE

        // Make the remove and info buttons visible and functional
        holder.removeCharacterButton.visibility = View.VISIBLE
        holder.characterSheetButton.visibility = View.VISIBLE

        holder.removeCharacterButton.setOnClickListener {
            onDeleteClicked(blueprint)
        }

        holder.characterSheetButton.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, CharacterBlueprintSheetActivity::class.java).apply {
                putExtra(CharacterBlueprintSheetActivity.BLUEPRINT_ID, blueprint.id)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = blueprints.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateBlueprints(newBlueprints: List<CharacterBlueprint>) {
        blueprints = newBlueprints
        notifyDataSetChanged()
    }
}