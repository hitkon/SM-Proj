package com.example.dm_helper

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import java.util.Collections

class InitiativeAdapter(
    private val characters: MutableList<Character>,
    private val itemTouchHelper: ItemTouchHelper
) : RecyclerView.Adapter<InitiativeAdapter.InitiativeViewHolder>() {

    class InitiativeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val characterPortrait: ImageView = itemView.findViewById(R.id.character_portrait)
        val characterName: TextView = itemView.findViewById(R.id.character_name)
        val initiativeValue: TextView = itemView.findViewById(R.id.initiative_value)
        val conditionIconsLayout: LinearLayout = itemView.findViewById(R.id.condition_icons_layout)
        val hpValue: TextView = itemView.findViewById(R.id.hp_value)
        val dragHandle: ImageView = itemView.findViewById(R.id.drag_handle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InitiativeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.initiative_list_item, parent, false)
        return InitiativeViewHolder(itemView)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: InitiativeViewHolder, position: Int) {
        val currentCharacter = characters[position]
        holder.characterPortrait.setImageResource(currentCharacter.portrait)
        holder.characterName.text = currentCharacter.name
        holder.initiativeValue.text = currentCharacter.initiative.toString()
        holder.hpValue.text = "${currentCharacter.currentHP}/${currentCharacter.maximumHP}"

        holder.conditionIconsLayout.removeAllViews()
        for (condition in currentCharacter.conditions) {
            val imageView = ImageView(holder.itemView.context)
            imageView.setImageResource(condition)
            val layoutParams = LinearLayout.LayoutParams(48, 48)
            layoutParams.marginEnd = 8
            imageView.layoutParams = layoutParams
            holder.conditionIconsLayout.addView(imageView)
        }

        holder.dragHandle.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                itemTouchHelper.startDrag(holder)
            }
            true
        }
    }

    override fun getItemCount() = characters.size

    fun moveItem(from: Int, to: Int) {
        Collections.swap(characters, from, to)
        notifyItemMoved(from, to)
    }
}