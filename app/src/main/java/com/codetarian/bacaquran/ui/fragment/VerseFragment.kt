package com.codetarian.bacaquran.ui.fragment

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.codetarian.bacaquran.databinding.FragmentVerseBinding
import com.codetarian.bacaquran.db.entity.Surah
import com.codetarian.bacaquran.ui.activity.RecitationActivity
import com.codetarian.bacaquran.ui.adapter.VerseRVAdapter
import com.codetarian.bacaquran.ui.viewmodel.QuranViewModel
import com.codetarian.bacaquran.utils.Coroutines
import com.codetarian.bacaquran.utils.UtilExtensions.openActivity
import com.codetarian.bacaquran.utils.constant.StringConstants
import com.codetarian.bacaquran.utils.ext.forEachVisibleHolder

class VerseFragment(val surah: Surah) : Fragment() {

    private val viewModel by viewModels<QuranViewModel>()
    private lateinit var binding: FragmentVerseBinding
    private lateinit var verseRVAdapter: VerseRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerseBinding.inflate(inflater, container, false)

        verseRVAdapter = VerseRVAdapter(
            context = requireContext(),
            onItemClicked = {
                openActivity(RecitationActivity::class.java) {
                    putParcelable(RecitationActivity.EXTRA_VERSE, it)
                    putString(RecitationActivity.EXTRA_SURAH_NAME, surah.transliteration)
                }
            },
            onBookmarkClicked = {
                Coroutines.io {
                    viewModel.updateVerseBookmark(it.id)
                }
            }
        )

        initView()
        observeVerses()
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

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvVerse.apply {
            layoutManager = linearLayoutManager
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    linearLayoutManager.orientation
                )
            )
            isNestedScrollingEnabled = false
            adapter = verseRVAdapter
        }
        ViewCompat.setNestedScrollingEnabled(binding.rvVerse, false)
    }

    private fun setupToolbar() {
        val scrollBounds = Rect()
        binding.nestedScrollView.setOnScrollChangeListener { _, _, _, _, _ ->
            binding.nestedScrollView.getDrawingRect(scrollBounds)
            binding.rvVerse.forEachVisibleHolder { holder ->
                val itemView = holder.itemView
                val isVisible = itemView.getLocalVisibleRect(scrollBounds)

                if (isVisible) {
                    val visibleWidth = scrollBounds.width()
                    val fullyVisible = visibleWidth >= itemView.width

                    if (fullyVisible) {
                        val item = verseRVAdapter.currentList[holder.adapterPosition]
                        (activity as? VerseFragmentListener)?.onTitleChanged(String.format(StringConstants.JUZ_TITLE, item.juz))
                    }
                }
            }
        }
    }

    fun initView() {
        setupHeaddress()
        setupBismillah()
        setupRecyclerView()
        setupToolbar()
    }

    private fun observeVerses() {
        Coroutines.main {
            viewModel.loadVersesBySurah(surah.id).observe(viewLifecycleOwner) {
                verseRVAdapter.submitList(it)
            }
        }
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