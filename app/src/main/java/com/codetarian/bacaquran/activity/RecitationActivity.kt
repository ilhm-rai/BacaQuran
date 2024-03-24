package com.codetarian.bacaquran.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codetarian.bacaquran.databinding.ActivityRecitationBinding
import com.codetarian.bacaquran.domain.Verse
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper

class RecitationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecitationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecitationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val verse = getExtraVerse()

        setupToolbar()
        setupVerseLayout(verse)
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
        binding.verseLayout.apply {
            ayah.text = verse.ayah.toString()
            arabic.text = verse.arabicIndopak
            latin.text = verse.latin
            translation.text = verse.translation
        }
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

    companion object {
        const val EXTRA_VERSE = "extra_verse"
        const val EXTRA_SURAH_NAME = "extra_surah_name"
    }
}