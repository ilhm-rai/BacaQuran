package com.codetarian.bacaquran.ui.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.codetarian.bacaquran.R
import com.codetarian.bacaquran.db.entity.Verse

class VerseRVAdapter(
    private val context: Context,
    private val verseClickInterface: VerseClickInterface
) : RecyclerView.Adapter<VerseRVAdapter.ViewHolder>() {

    private val surahVerses = ArrayList<Verse>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mTextViewAyah: TextView = itemView.findViewById(R.id.text_ayah)
        val mTextViewArabic: TextView = itemView.findViewById(R.id.text_arabic)
        val mTextViewLatin: TextView = itemView.findViewById(R.id.text_latin)
        val mTextViewTranslation: TextView = itemView.findViewById(R.id.text_translation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_verse, parent, false)
        val typeface: Typeface? = ResourcesCompat.getFont(context, R.font.alquran_indopak_by_quranwbw)
        return ViewHolder(view).apply {
            mTextViewArabic.typeface = typeface
        }
    }

    override fun getItemCount(): Int = surahVerses.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = surahVerses[position]
        with(holder) {
            mTextViewAyah.text = item.ayah.toString()
            mTextViewArabic.text = item.arabicIndopak
            mTextViewLatin.text = item.latin
            mTextViewTranslation.text = item.translation

            itemView.setOnClickListener {
                verseClickInterface.onVerseClick(item)
            }
        }
    }

    fun updateList(newList: List<Verse>) {
        surahVerses.clear()
        surahVerses.addAll(newList)
        notifyDataSetChanged()
    }

    fun getItem(position: Int) = surahVerses[position]
}

interface VerseClickInterface {
    // creating a method for click action
    // on recycler view item for updating it.
    fun onVerseClick(verse: Verse)
}