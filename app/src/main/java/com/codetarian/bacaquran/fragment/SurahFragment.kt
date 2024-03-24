package com.codetarian.bacaquran.fragment

import android.content.Intent
import android.os.Bundle
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

class SurahFragment : Fragment(), SurahClickInterface {

    private lateinit var binding: FragmentSurahBinding
    private lateinit var viewModel: SurahViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSurahBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[SurahViewModel::class.java]

        viewModel.allSurah.observe(viewLifecycleOwner) { list ->
            list?.let {
                setupRecyclerView(it)
            }
        }

        return binding.root
    }

    private fun setupRecyclerView(list: List<Surah>) {
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val surahRVAdapter by lazy { SurahRVAdapter(this.requireContext(), this) }
        surahRVAdapter.updateList(list)

        binding.recyclerview.apply {
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

    override fun onSurahClick(surah: Surah) {
        val verseActivity = Intent(context, VerseActivity::class.java)
        verseActivity.putExtra(VerseActivity.EXTRA_SURAH_ID, surah.id)
        startActivity(verseActivity)
    }
}