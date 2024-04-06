package com.codetarian.bacaquran.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.codetarian.bacaquran.databinding.FragmentSurahBinding
import com.codetarian.bacaquran.ui.activity.VerseActivity
import com.codetarian.bacaquran.ui.adapter.SurahRVAdapter
import com.codetarian.bacaquran.ui.viewmodel.QuranViewModel
import com.codetarian.bacaquran.utils.Coroutines
import com.codetarian.bacaquran.utils.UtilExtensions.openActivity

class SurahFragment : Fragment() {

    private val viewModel by viewModels<QuranViewModel>()
    private lateinit var binding: FragmentSurahBinding
    private lateinit var surahRVAdapter: SurahRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSurahBinding.inflate(inflater, container, false)

        surahRVAdapter = SurahRVAdapter(requireContext()) {
            openActivity(VerseActivity::class.java) {
                putInt(VerseActivity.EXTRA_SURAH_ID, it.id)
            }
        }

        initView()
        observeSurah()
        return binding.root
    }

    private fun initView() {
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvSurah.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    linearLayoutManager.orientation
                )
            )
            adapter = surahRVAdapter
        }
    }

    private fun observeSurah() {
        Coroutines.main {
            viewModel.loadSurahData().observe(viewLifecycleOwner) {
                surahRVAdapter.submitList(it)
            }
        }
    }
}