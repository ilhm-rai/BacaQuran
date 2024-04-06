package com.codetarian.bacaquran.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codetarian.bacaquran.databinding.ItemVerseBinding
import com.codetarian.bacaquran.db.entity.Verse

class VerseRVAdapter(
    private val context: Context,
    private val listener: (Verse) -> Unit
) : ListAdapter<Verse, VerseRVAdapter.VerseViewHolder>(DiffUtilVerse()) {

    inner class VerseViewHolder(private val binding: ItemVerseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(verse: Verse, listener: (Verse) -> Unit) {
            binding.apply {
                textAyah.text = verse.ayah.toString()
                textArabic.text = verse.arabicIndopak
                textLatin.text = verse.latin
                textTranslation.text = verse.translation

                binding.root.setOnClickListener {
                    listener(verse)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerseViewHolder {
        val binding = ItemVerseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VerseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VerseViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindItem(item, listener)
    }

    private class DiffUtilVerse : DiffUtil.ItemCallback<Verse>() {
        override fun areItemsTheSame(oldItem: Verse, newItem: Verse): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: Verse, newItem: Verse): Boolean {
            return newItem == oldItem
        }

    }
}