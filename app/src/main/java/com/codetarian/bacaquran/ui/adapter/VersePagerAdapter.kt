package com.codetarian.bacaquran.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.codetarian.bacaquran.db.entity.Surah
import com.codetarian.bacaquran.ui.fragment.VerseFragment

import androidx.recyclerview.widget.DiffUtil

class VersePagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private var surahList: List<Surah> = emptyList()

    fun submitList(newList: List<Surah>) {
        val diffResult = DiffUtil.calculateDiff(SurahDiffCallback(surahList, newList))
        surahList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    fun getSurah(position: Int) = surahList[position]

    override fun getItemCount(): Int = surahList.size

    override fun createFragment(position: Int): Fragment {
        return VerseFragment(surahList[position])
    }

    private class SurahDiffCallback(
        private val oldList: List<Surah>,
        private val newList: List<Surah>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
