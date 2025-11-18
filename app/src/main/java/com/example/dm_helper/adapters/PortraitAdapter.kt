package com.example.dm_helper.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dm_helper.data.Portrait
import com.example.dm_helper.databinding.ItemPortraitBinding

class PortraitAdapter(private val portraits: List<Portrait>) :
    RecyclerView.Adapter<PortraitAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemPortraitBinding) :
            RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPortraitBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val portrait = portraits[position]
        holder.binding.imagePortrait.setImageURI(Uri.parse(portrait.imageUri))
    }

    override fun getItemCount(): Int = portraits.size
}