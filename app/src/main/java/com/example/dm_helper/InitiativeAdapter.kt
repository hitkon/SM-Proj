package com.example.dm_helper

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.Collections

class InitiativeAdapter(
    private val characters: MutableList<Character>,
    private val onDeleteCharacter: (Character) -> Unit,
    private val onStartDrag: (RecyclerView.ViewHolder) -> Unit
) : RecyclerView.Adapter<InitiativeAdapter.InitiativeViewHolder>() {

    class InitiativeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val characterPortrait: ImageView = itemView.findViewById(R.id.character_portrait)
        val hpValue: TextView = itemView.findViewById(R.id.hp_value)
        val removeCharacterButton: ImageButton = itemView.findViewById(R.id.remove_character_button)
        val uploadImageButton: ImageButton = itemView.findViewById(R.id.upload_image_button)
        val characterName: TextView = itemView.findViewById(R.id.character_name)
        val conditionIconsLayout: LinearLayout = itemView.findViewById(R.id.condition_icons_layout)
        val initiativeValue: TextView = itemView.findViewById(R.id.initiative_value)
        val dragHandle: ImageView = itemView.findViewById(R.id.drag_handle)
        val characterSheetButton: ImageButton = itemView.findViewById(R.id.character_sheet_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InitiativeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.initiative_list_item, parent, false)
        return InitiativeViewHolder(itemView)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: InitiativeViewHolder, position: Int) {
        val currentCharacter = characters[position]

        if (!currentCharacter.imageUrl.isNullOrEmpty()) {
            Glide.with(holder.itemView.context)
                .load(currentCharacter.imageUrl.toUri())
                .into(holder.characterPortrait)
        } else {
            holder.characterPortrait.setImageResource(currentCharacter.portrait)
        }

        holder.characterName.text = currentCharacter.name
        holder.initiativeValue.text = currentCharacter.initiative.toString()
        holder.hpValue.text = "${currentCharacter.currentHP}/${currentCharacter.maximumHP}"

        holder.uploadImageButton.visibility = View.GONE

        holder.conditionIconsLayout.removeAllViews()
        val conditionIcons = ConditionHelper.getConditions(currentCharacter)
        for (condition in conditionIcons) {
            val imageView = ImageView(holder.itemView.context)
            imageView.setImageResource(condition.iconRes)
            val layoutParams = LinearLayout.LayoutParams(48, 48)
            layoutParams.marginEnd = 8
            imageView.layoutParams = layoutParams
            holder.conditionIconsLayout.addView(imageView)
        }

        holder.dragHandle.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                onStartDrag(holder)
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
}