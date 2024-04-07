package com.codetarian.bacaquran.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codetarian.bacaquran.databinding.ItemBookmarkBinding
import com.codetarian.bacaquran.db.entity.VerseAndSurah
import com.codetarian.bacaquran.utils.DateUtil
import com.codetarian.bacaquran.utils.constant.StringConstants

class BookmarkRVAdapter(
    private val context: Context,
    private val onItemClicked: (VerseAndSurah) -> Unit,
    private val onBookmarkClicked: (VerseAndSurah) -> Unit
) : ListAdapter<VerseAndSurah, BookmarkRVAdapter.BookmarkViewHolder>(DiffUtilBookmark()) {

    class BookmarkViewHolder(private val binding: ItemBookmarkBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(verseAndSurah: VerseAndSurah,
                     onItemClicked: (VerseAndSurah) -> Unit, onBookmarkClicked: (VerseAndSurah) -> Unit) {
            binding.apply {
                val mTextBookmarkedVerse = String.format(
                    StringConstants.BOOKMARKED_VERSE,
                    verseAndSurah.transliteration,
                    verseAndSurah.verse.ayah,
                    verseAndSurah.verse.juz
                    )
                textBookmarkedVerse.text = mTextBookmarkedVerse
                verseAndSurah.verse.bookmarkedAt?.let {
                    val mTextBookmarkedAt = DateUtil.formatDateRelativeToNow(it)
                    textBookmarkedAt.text = mTextBookmarkedAt
                }
                verseBinding.apply {
                    textAyah.text = verseAndSurah.verse.ayah.toString()
                    textArabic.text = verseAndSurah.verse.arabicIndopak
                    textLatin.text = verseAndSurah.verse.latin
                    textTranslation.text = verseAndSurah.verse.translation
                    ibBookmark.isSelected = verseAndSurah.verse.isBookmarked
                    ibBookmark.setOnClickListener {
                        onBookmarkClicked(verseAndSurah)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val binding = ItemBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookmarkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindItem(item, onItemClicked, onBookmarkClicked)
    }


    class DiffUtilBookmark : DiffUtil.ItemCallback<VerseAndSurah>() {
        override fun areItemsTheSame(oldItem: VerseAndSurah, newItem: VerseAndSurah): Boolean {
            return newItem.verse.id == oldItem.verse.id
        }

        override fun areContentsTheSame(oldItem: VerseAndSurah, newItem: VerseAndSurah): Boolean {
            return newItem == oldItem
        }

    }
}