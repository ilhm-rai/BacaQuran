package com.codetarian.bacaquran.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.codetarian.bacaquran.activity.VerseActivity
import com.codetarian.bacaquran.adapter.SurahClickInterface
import com.codetarian.bacaquran.adapter.SurahRVAdapter
import com.codetarian.bacaquran.databinding.FragmentSurahBinding
import com.codetarian.bacaquran.domain.Surah
import com.codetarian.bacaquran.viewmodel.SurahViewModel
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper


class SurahFragment : Fragment(), SurahClickInterface {

    private lateinit var binding: FragmentSurahBinding
    private lateinit var viewModel: SurahViewModel
    private lateinit var allSurah: ArrayList<Surah>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSurahBinding.inflate(inflater, container, false)
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val surahRVAdapter by lazy { SurahRVAdapter(this.requireContext(), this) }
        
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[SurahViewModel::class.java]

        viewModel.allSurah.observe(viewLifecycleOwner) { list ->
            list?.let {
                allSurah = ArrayList(it)
                surahRVAdapter.updateList(it)
            }
        }

        binding.apply {
            recyclerview.apply {
                adapter = surahRVAdapter
                layoutManager = linearLayoutManager
                addItemDecoration(
                    DividerItemDecoration(
                        context,
                        linearLayoutManager.orientation
                    )
                )
            }
        }

        return binding.root
    }

    override fun onSurahClick(surah: Surah) {
        val verseActivity = Intent(context, VerseActivity::class.java)
        verseActivity.putExtra(VerseActivity.EXTRA_SURAH_ID, surah.id)
        if (Build.VERSION.SDK_INT >= 33) {
            verseActivity.putParcelableArrayListExtra(VerseActivity.EXTRA_ALL_SURAH, allSurah)
        } else {
            val objectMapper = ObjectMapper()
            try {
                val jsonAllSurah = objectMapper.writeValueAsString(allSurah)
                verseActivity.putExtra(VerseActivity.EXTRA_ALL_SURAH, jsonAllSurah)
            } catch (e: JsonProcessingException) {
                Log.e("ERROR", "${e.message}")
            }
        }
        startActivity(verseActivity)
    }
}