package com.example.dm_helper

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import java.util.Collections

class InitiativeAdapter(
    private val characters: MutableList<Character>,
    private val itemTouchHelper: ItemTouchHelper,
    private val onDeleteCharacter: (Character) -> Unit
) : RecyclerView.Adapter<InitiativeAdapter.InitiativeViewHolder>() {

    class InitiativeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val characterPortrait: ImageView = itemView.findViewById(R.id.character_portrait)
        val characterName: TextView = itemView.findViewById(R.id.character_name)
        val initiativeValue: TextView = itemView.findViewById(R.id.initiative_value)
        val conditionIconsLayout: LinearLayout = itemView.findViewById(R.id.condition_icons_layout)
        val hpValue: TextView = itemView.findViewById(R.id.hp_value)
        val dragHandle: ImageView = itemView.findViewById(R.id.drag_handle)
        val characterSheetButton: ImageButton = itemView.findViewById(R.id.character_sheet_button)
        val removeCharacterButton: ImageButton = itemView.findViewById(R.id.remove_character_button)
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
        val conditions = ConditionHelper.getConditions(currentCharacter)
        for (condition in conditions) {
            holder.conditionIconsLayout.addView(
                createConditionView(holder.itemView, condition)
            )
        }

        holder.dragHandle.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                itemTouchHelper.startDrag(holder)
            }
            true
        }

        holder.characterSheetButton.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, CharacterSheetActivity::class.java).apply {
                putExtra(CharacterSheetActivity.CHARACTER_ID, currentCharacter.id)
            }
            context.startActivity(intent)
        }

        holder.removeCharacterButton.setOnClickListener {
            onDeleteCharacter(currentCharacter)
        }
    }

    override fun getItemCount() = characters.size

    fun moveItem(from: Int, to: Int) {
        Collections.swap(characters, from, to)
        notifyItemMoved(from, to)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCharacters(newCharacters: List<Character>) {
        characters.clear()
        characters.addAll(newCharacters)
        notifyDataSetChanged()
    }

    private fun createConditionView(
        parent: View,
        condition: ConditionUi
    ): FrameLayout {

        val context = parent.context

        val container = FrameLayout(context).apply {
            layoutParams = LinearLayout.LayoutParams(48, 48).apply {
                marginEnd = 8
            }
        }

        val icon = ImageView(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            setImageResource(condition.iconRes)
        }

        container.addView(icon)

        if (condition.value > 0) {
            val badge = TextView(context).apply {
                layoutParams = FrameLayout.LayoutParams(20, 20).apply {
                    gravity = android.view.Gravity.BOTTOM or android.view.Gravity.END
                }
                background = context.getDrawable(R.drawable.condition_badge)
                text = condition.value.toString()
                setTextColor(android.graphics.Color.WHITE)
                textSize = 10f
                gravity = android.view.Gravity.CENTER
                setTypeface(null, android.graphics.Typeface.BOLD)
            }
            container.addView(badge)
        }

        return container
    }
}