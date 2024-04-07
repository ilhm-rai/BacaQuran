package com.codetarian.bacaquran.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codetarian.bacaquran.databinding.ItemJuzBinding
import com.codetarian.bacaquran.db.entity.Juz

class JuzRVAdapter(
    private val context: Context,
    private val onItemClicked: (Juz) -> Unit
) : ListAdapter<Juz, JuzRVAdapter.ViewHolder>(DiffUtilJuz()) {

    inner class ViewHolder(private val binding: ItemJuzBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(juz: Juz, onItemClicked: (Juz) -> Unit) {
            binding.apply {
                textJuz.text = juz.juz.toString()
                val juzTitle = "Juz ${juz.juz}"
                textJuzTitle.text = juzTitle
                val juzContent =
                    "${juz.firstSurahName} ${juz.firstAyah} - ${juz.lastSurahName} ${juz.lastAyah}"
                textJuzContent.text = juzContent
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemJuzBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindItem(item, onItemClicked)
    }


    class DiffUtilJuz : DiffUtil.ItemCallback<Juz>() {
        override fun areItemsTheSame(oldItem: Juz, newItem: Juz): Boolean {
            return newItem.juz == oldItem.juz
        }

        override fun areContentsTheSame(oldItem: Juz, newItem: Juz): Boolean {
            return newItem == oldItem
        }
    }
}