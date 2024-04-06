package com.codetarian.bacaquran.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.codetarian.bacaquran.databinding.FragmentJuzBinding
import com.codetarian.bacaquran.ui.adapter.JuzRVAdapter
import com.codetarian.bacaquran.ui.viewmodel.QuranViewModel
import com.codetarian.bacaquran.utils.Coroutines

class JuzFragment : Fragment() {

    private val viewModel by viewModels<QuranViewModel>()
    private lateinit var binding: FragmentJuzBinding
    private lateinit var juzRVAdapter: JuzRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJuzBinding.inflate(inflater, container, false)

        juzRVAdapter = JuzRVAdapter(requireContext()) {}

        setupRecyclerView()
        observeJuz()
        return binding.root
    }

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.rvJuz.apply {
            layoutManager = linearLayoutManager
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    linearLayoutManager.orientation
                )
            )
            adapter = juzRVAdapter
        }
    }

    fun observeJuz() {
        Coroutines.main {
            viewModel.loadJuzData().observe(viewLifecycleOwner) {
                juzRVAdapter.submitList(it)
            }
        }
    }
}