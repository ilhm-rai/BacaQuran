package com.codetarian.bacaquran.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.codetarian.bacaquran.R
import com.codetarian.bacaquran.domain.Verse

class VerseRVAdapter(
    private val context: Context,
) : RecyclerView.Adapter<VerseRVAdapter.ViewHolder>() {

    private val surahVerses = ArrayList<Verse>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ayah: TextView = itemView.findViewById(R.id.ayah)
        val arabic: TextView = itemView.findViewById(R.id.arabic)
        val latin: TextView = itemView.findViewById(R.id.latin)
        val translation: TextView = itemView.findViewById(R.id.translation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val typeface: Typeface? = ResourcesCompat.getFont(context, R.font.alquran_indopak_by_quranwbw)
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_verse, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.arabic.typeface = typeface
        return viewHolder
    }

    override fun getItemCount(): Int = surahVerses.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = surahVerses[position]
        holder.ayah.text = item.ayah.toString()
        holder.arabic.text = item.arabicIndopak
        holder.latin.text = item.latin
        holder.translation.text = item.translation
    }

    fun updateList(newList: List<Verse>) {
        surahVerses.clear()
        surahVerses.addAll(newList)
        notifyDataSetChanged()
    }
}