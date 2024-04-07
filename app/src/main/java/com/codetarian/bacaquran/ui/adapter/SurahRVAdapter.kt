package com.codetarian.bacaquran.ui.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codetarian.bacaquran.R
import com.codetarian.bacaquran.databinding.ItemSurahBinding
import com.codetarian.bacaquran.db.entity.Surah

class SurahRVAdapter(
    private val context: Context,
    private val onItemClicked: (Surah) -> Unit
) : ListAdapter<Surah, SurahRVAdapter.SurahViewHolder>(DiffUtilSurah()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurahViewHolder {
        val binding = ItemSurahBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SurahViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SurahViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindItem(item, onItemClicked)
    }

    inner class SurahViewHolder(private val binding: ItemSurahBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(surah: Surah, listener: (Surah) -> Unit) {
            binding.apply {
                textSurahId.text = surah.id.toString()
                textTransliteration.text = surah.transliteration
                textArabic.text = surah.quranSurahFont

                val surahInfo = "${surah.translation} (${surah.numAyah} Ayat)"
                textSurahInfo.text = surahInfo

                val fontType = if (surah.id < 60) R.font.quran_surah_1 else R.font.quran_surah_2
                val typeface: Typeface? = ResourcesCompat.getFont(context, fontType)
                textArabic.typeface = typeface

                binding.root.setOnClickListener {
                    listener(surah)
                }
            }
        }
    }

    private class DiffUtilSurah : DiffUtil.ItemCallback<Surah>() {
        override fun areItemsTheSame(oldItem: Surah, newItem: Surah): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: Surah, newItem: Surah): Boolean {
            return newItem == oldItem
        }

    }
}
