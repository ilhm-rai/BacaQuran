package com.codetarian.bacaquran.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.codetarian.bacaquran.domain.Surah
import com.codetarian.bacaquran.R

class SurahRVAdapter(
    private val context: Context,
    private val surahClickInterface: SurahClickInterface
) : RecyclerView.Adapter<SurahRVAdapter.ViewHolder>() {

    private val listSurah = ArrayList<Surah>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mTextViewSurahId: TextView = itemView.findViewById(R.id.text_surah_id)
        val mTextViewTransliteration: TextView = itemView.findViewById(R.id.text_transliteration)
        val mTextViewSurahInfo: TextView = itemView.findViewById(R.id.text_surah_info)
        val mTextViewArabic: TextView = itemView.findViewById(R.id.text_arabic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_surah, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listSurah.size

    override fun onBindViewHolder(holder: SurahRVAdapter.ViewHolder, position: Int) {
        val item = listSurah[position]

        with(holder) {
            mTextViewSurahId.text = item.id.toString()
            mTextViewTransliteration.text = item.transliteration
            mTextViewArabic.text = item.quranSurahFont

            val surahInfo = "${item.translation} (${item.numAyah} Ayat)"
            mTextViewSurahInfo.text = surahInfo

            val fontType = if (item.id < 60) R.font.quran_surah_1 else R.font.quran_surah_2
            val typeface: Typeface? = ResourcesCompat.getFont(context, fontType)
            mTextViewArabic.typeface = typeface

            itemView.setOnClickListener {
                surahClickInterface.onSurahClick(listSurah[position])
            }
        }
    }

    fun updateList(newList: List<Surah>) {
        listSurah.clear()
        listSurah.addAll(newList)
        notifyDataSetChanged()
    }
}

interface SurahClickInterface {
    // creating a method for click action
    // on recycler view item for updating it.
    fun onSurahClick(surah: Surah)
}
