package com.codetarian.bacaquran.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.codetarian.bacaquran.activity.RecitationActivity
import com.codetarian.bacaquran.adapter.VerseClickInterface
import com.codetarian.bacaquran.adapter.VerseRVAdapter
import com.codetarian.bacaquran.databinding.FragmentVerseBinding
import com.codetarian.bacaquran.domain.Surah
import com.codetarian.bacaquran.domain.Verse
import com.codetarian.bacaquran.viewmodel.VerseViewModel
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper

class VerseFragment(val surah: Surah) : Fragment(), VerseClickInterface {

    private lateinit var binding: FragmentVerseBinding
    private lateinit var viewModel: VerseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerseBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[VerseViewModel::class.java]

        viewModel.loadVersesBySurah(surah.id).observe(viewLifecycleOwner) { list ->
            list?.let {
                setupRecyclerView(it)
            }
        }

        setupHeaddress()
        setupBismillah()

        return binding.root
    }

    private fun setupHeaddress() {
        val ayah = "${surah.numAyah} Ayat"
        binding.apply {
            headAyah.text = ayah
            headLocation.text = surah.location
            mainContent.rotationY = 180f
            headSurah.text = surah.id.toString()
        }
    }

    private fun setupBismillah() {
        if (surah.id == AL_FATIHAH || surah.id == AT_TAUBAH) {
            binding.bismillahParent.visibility = View.GONE
        }
    }

    private fun setupRecyclerView(list: List<Verse>) {
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val verseRVAdapter by lazy { VerseRVAdapter(this.requireContext(), this) }
        verseRVAdapter.updateList(list)

        binding.recyclerview.apply {
            adapter = verseRVAdapter
            layoutManager = linearLayoutManager
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    linearLayoutManager.orientation
                )
            )
            isNestedScrollingEnabled = false
        }
        ViewCompat.setNestedScrollingEnabled(binding.recyclerview, false)
    }

    override fun onVerseClick(verse: Verse) {
        val recitationActivity = Intent(context, RecitationActivity::class.java)
        recitationActivity.putExtra(RecitationActivity.EXTRA_SURAH_NAME, surah.transliteration)
        if (Build.VERSION.SDK_INT >= 33) {
            recitationActivity.putExtra(RecitationActivity.EXTRA_VERSE, verse)
        } else {
            val objectMapper = ObjectMapper()
            try {
                val jsonVerse = objectMapper.writeValueAsString(verse)
                recitationActivity.putExtra(RecitationActivity.EXTRA_VERSE, jsonVerse)
            } catch (e: JsonProcessingException) {
                Log.e("ERROR", "${e.message}")
            }
        }
        startActivity(recitationActivity)
    }

    companion object {
        const val AL_FATIHAH = 1
        const val AT_TAUBAH = 9
    }
}