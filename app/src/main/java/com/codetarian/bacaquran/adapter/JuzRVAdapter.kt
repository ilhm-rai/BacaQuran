package com.codetarian.bacaquran.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codetarian.bacaquran.R
import com.codetarian.bacaquran.domain.Juz

class JuzRVAdapter(
    private val context: Context
) : RecyclerView.Adapter<JuzRVAdapter.ViewHolder>() {

    private val listJuz = ArrayList<Juz>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mTextJuz: TextView = itemView.findViewById(R.id.text_juz)
        val mTextJuzTitle: TextView = itemView.findViewById(R.id.text_juz_title)
        val mTextJuzContent: TextView = itemView.findViewById(R.id.text_juz_content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_juz, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listJuz.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listJuz[position]

        with(holder) {
            mTextJuz.text = item.juz.toString()
            val juzTitle = "Juz ${item.juz}"
            mTextJuzTitle.text = juzTitle
            val juzContent =
                "${item.firstSurahName} ${item.firstAyah} - ${item.lastSurahName} ${item.lastAyah}"
            mTextJuzContent.text = juzContent
        }
    }

    fun updateList(newList: List<Juz>) {
        listJuz.clear()
        listJuz.addAll(newList)
        notifyDataSetChanged()
    }
}