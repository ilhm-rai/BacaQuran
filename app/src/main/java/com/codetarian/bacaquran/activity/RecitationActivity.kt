package com.codetarian.bacaquran.activity

import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.codetarian.bacaquran.R
import com.codetarian.bacaquran.adapter.RecitationRVAdapter
import com.codetarian.bacaquran.databinding.ActivityRecitationBinding
import com.codetarian.bacaquran.domain.Verse
import com.codetarian.bacaquran.util.LevenshteinDistance
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.difflib.DiffUtils

class RecitationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecitationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecitationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val verse = getExtraVerse()
        val recitationVerse = "بِسْمِ ٱللَّهِ ٱلرَّحْمَـٰنِ ٱلرَّحِيم"

        setupToolbar()

        setupVerseLayout(verse)
        setupRecyclerView(verse.arabic, recitationVerse)
        binding.textTranscription.text = highlightDifferences(verse.arabic, recitationVerse)
        val txtDistance = "Distance: ${LevenshteinDistance.calculate(verse.arabic, recitationVerse)}"
        binding.textLevenshteinDistance.text = txtDistance
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun setupToolbar() {
        val surahName = intent.getStringExtra(EXTRA_SURAH_NAME)
        binding.toolbar.title = surahName
    }

    private fun setupVerseLayout(verse: Verse) {
        binding.verseBinding.apply {
            textAyah.text = verse.ayah.toString()
            textArabic.text = verse.arabic
            textLatin.text = verse.latin
            textTranslation.text = verse.translation
        }
    }

    private fun setupRecyclerView(originalText: String, modifiedText: String) {
        val patches = DiffUtils.diff(originalText.split(""), modifiedText.split(""))
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val recitationRVAdapter by lazy { RecitationRVAdapter(this, patches) }

        binding.recyclerview.apply {
            adapter = recitationRVAdapter
            layoutManager = linearLayoutManager
            isNestedScrollingEnabled = false
        }
        ViewCompat.setNestedScrollingEnabled(binding.recyclerview, false)
    }

    private fun getExtraVerse(): Verse {
        return if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_VERSE, Verse::class.java)!!
        } else {
            val mapper = ObjectMapper()
            val jsonVerse = intent.getStringExtra(EXTRA_VERSE)
            mapper.readValue(jsonVerse, object : TypeReference<Verse>() {})
        }
    }

    private fun highlightDifferences(originalText: String, modifiedText: String): SpannableString {
        val patches = DiffUtils.diff(originalText.split(""), modifiedText.split(""))

        val spannableString = SpannableString(modifiedText)

        for (delta in patches.deltas) {
            val startIndex = maxOf(delta.target.position - 1, 0)
            val endIndex = minOf(startIndex + delta.target.lines.joinToString("").length, spannableString.length)
            val color = getColor(R.color.cinnabar)

            val start = minOf(startIndex, endIndex)
            val end = maxOf(startIndex, endIndex)

            spannableString.setSpan(ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        return spannableString
    }

    companion object {
        const val EXTRA_VERSE = "extra_verse"
        const val EXTRA_SURAH_NAME = "extra_surah_name"
    }
}