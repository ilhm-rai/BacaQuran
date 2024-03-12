package com.codetarian.bacaquran.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.codetarian.bacaquran.domain.SurahDomain
import com.codetarian.bacaquran.R

class SurahAdapter(private val items: List<SurahDomain>) : RecyclerView.Adapter<SurahAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val surahId: TextView = itemView.findViewById(R.id.surahId)
        val transliteration: TextView = itemView.findViewById(R.id.transliteration)
        val surahInfo: TextView = itemView.findViewById(R.id.surah_info)
        val arabic: TextView = itemView.findViewById(R.id.arabic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_surah, parent, false)
        val typeface: Typeface? = ResourcesCompat.getFont(parent.context, R.font.quran_surah)
        val viewHolder = ViewHolder(view)
        viewHolder.arabic.typeface = typeface
        return viewHolder
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SurahAdapter.ViewHolder, position: Int) {
        val item = items[position]
        val surahInfo = "${item.location} | ${item.numAyah} Ayat"
        holder.surahId.text = item.id.toString()
        holder.transliteration.text = item.transliteration
        holder.surahInfo.text = surahInfo
        holder.arabic.text = item.id.toString()
    }
}