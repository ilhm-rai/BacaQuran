package com.codetarian.bacaquran.fragment

import android.content.Intent
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.widget.NestedScrollView
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
import com.codetarian.bacaquran.ext.forEachVisibleHolder
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
                setupToolbarTitle()
            }
        }

        setupHeaddress()
        setupBismillah()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? VerseFragmentListener)?.onPageChange(binding.nestedScrollView)
    }

    private fun setupHeaddress() {
        val ayah = "${surah.numAyah} Ayat"
        binding.apply {
            textHeadAyah.text = ayah
            textHeadLocation.text = surah.location
            textHeadSurah.text = surah.id.toString()
            mainContent.rotationY = 180f
        }
    }

    private fun setupBismillah() {
        if (surah.id == AL_FATIHAH || surah.id == AT_TAUBAH) {
            binding.containerBismillah.visibility = View.GONE
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

    private fun setupToolbarTitle() {
        val scrollBounds = Rect()
        val adapter = binding.recyclerview.adapter as VerseRVAdapter
        binding.nestedScrollView.setOnScrollChangeListener { _, _, _, _, _ ->
            binding.nestedScrollView.getDrawingRect(scrollBounds)
            binding.recyclerview.forEachVisibleHolder { holder ->
                val itemView = holder.itemView
                val isVisible = itemView.getLocalVisibleRect(scrollBounds)

                if (isVisible) {
                    val visibleWidth = scrollBounds.width()
                    val fullyVisible = visibleWidth >= itemView.width

                    if (fullyVisible) {
                        val item = adapter.getItem(holder.adapterPosition)
                        (activity as? VerseFragmentListener)?.onTitleChanged("Juz ${item.juz}")
                    }
                }
            }
        }
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

    interface VerseFragmentListener {
        fun onTitleChanged(title: String)
        fun onPageChange(nestedScrollView: NestedScrollView)
    }
}