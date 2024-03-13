package com.codetarian.bacaquran.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.codetarian.bacaquran.domain.SurahDomain
import com.codetarian.bacaquran.R

class SurahRVAdapter(
    val context: Context,
    val surahClickInterface: SurahClickInterface
) : RecyclerView.Adapter<SurahRVAdapter.ViewHolder>() {

    private val allSurah = ArrayList<SurahDomain>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val surahId: TextView = itemView.findViewById(R.id.surahId)
        val transliteration: TextView = itemView.findViewById(R.id.transliteration)
        val surahInfo: TextView = itemView.findViewById(R.id.surah_info)
        val arabic: TextView = itemView.findViewById(R.id.arabic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_surah, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = allSurah.size

    override fun onBindViewHolder(holder: SurahRVAdapter.ViewHolder, position: Int) {
        val item = allSurah[position]
        val surahInfo = "${item.translation} (${item.numAyah} Ayat)"

        holder.surahId.text = item.id.toString()
        holder.transliteration.text = item.transliteration
        holder.surahInfo.text = surahInfo
        holder.arabic.text = item.quranSurahFont

        val fontType = if (item.id < 60) R.font.quran_surah_1 else R.font.quran_surah_2
        val typeface: Typeface? = ResourcesCompat.getFont(context, fontType)
        holder.arabic.typeface = typeface

        holder.itemView.setOnClickListener {
            surahClickInterface.onSurahClick(allSurah[position])
        }
    }

    fun updateList(newList: List<SurahDomain>) {
        allSurah.clear()
        allSurah.addAll(newList)
        notifyDataSetChanged()
    }
}

interface SurahClickInterface {
    // creating a method for click action
    // on recycler view item for updating it.
    fun onSurahClick(surah: SurahDomain)
}
