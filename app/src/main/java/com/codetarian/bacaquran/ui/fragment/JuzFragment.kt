package com.codetarian.bacaquran.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.codetarian.bacaquran.ui.adapter.JuzRVAdapter
import com.codetarian.bacaquran.databinding.FragmentJuzBinding
import com.codetarian.bacaquran.db.entity.Juz
import com.codetarian.bacaquran.ui.viewmodel.QuranViewModel

class JuzFragment : Fragment() {

    private lateinit var binding: FragmentJuzBinding
    private lateinit var viewModel: QuranViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJuzBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[QuranViewModel::class.java]

        viewModel.loadJuzData().observe(viewLifecycleOwner) { list ->
            list?.let {
                setupRecyclerView(it)
            }
        }

        return binding.root
    }

    private fun setupRecyclerView(list: List<Juz>) {

        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val surahRVAdapter = createJuzRVAdapter(list)

        binding.rvJuz.apply {
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

    private fun createJuzRVAdapter(list: List<Juz>): JuzRVAdapter {
        return JuzRVAdapter(requireContext()).apply {
            updateList(list)
        }
    }
}