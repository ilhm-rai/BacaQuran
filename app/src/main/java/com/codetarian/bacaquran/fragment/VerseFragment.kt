package com.codetarian.bacaquran.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.codetarian.bacaquran.R
import com.codetarian.bacaquran.adapter.VerseRVAdapter
import com.codetarian.bacaquran.databinding.FragmentVerseBinding
import com.codetarian.bacaquran.domain.Surah
import com.codetarian.bacaquran.viewmodel.VerseViewModel

class VerseFragment(val surah: Surah) : Fragment() {

    private lateinit var binding: FragmentVerseBinding
    private lateinit var viewModel: VerseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerseBinding.inflate(inflater, container, false)
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val verseRVAdapter by lazy { VerseRVAdapter(this.requireContext()) }

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[VerseViewModel::class.java]

        viewModel.loadVersesBySurah(surah.id).observe(viewLifecycleOwner) { list ->
            list?.let {
                verseRVAdapter.updateList(it)
            }
        }

        binding.apply {
            if (surah.id == 1) {
                bismillahParent.visibility = View.GONE
            }
            val ayah = "${surah.numAyah} Ayat"
            headAyah.text = ayah
            headLocation.text = surah.location
            mainContent.apply {
                rotationY = 180f
            }
            headSurah.apply {
                text = surah.id.toString()
                typeface = ResourcesCompat.getFont(context, R.font.arab_quran_islamic_140)
            }
            recyclerview.apply {
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
            ViewCompat.setNestedScrollingEnabled(recyclerview, false)
        }

        return binding.root
    }

}