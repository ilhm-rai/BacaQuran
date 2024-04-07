package com.codetarian.bacaquran.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.codetarian.bacaquran.databinding.FragmentBookmarkBinding
import com.codetarian.bacaquran.ui.adapter.BookmarkRVAdapter
import com.codetarian.bacaquran.ui.viewmodel.QuranViewModel
import com.codetarian.bacaquran.utils.Coroutines

class BookmarkFragment : Fragment() {

    private val viewModel by viewModels<QuranViewModel>()
    private lateinit var binding: FragmentBookmarkBinding
    private lateinit var bookmarkRVAdapter: BookmarkRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)

        bookmarkRVAdapter = BookmarkRVAdapter(
            context = requireContext(),
            onItemClicked = {
            },
            onBookmarkClicked = {
                Coroutines.io {
                    viewModel.updateVerseBookmark(it.verse.id)
                }
            }
        )

        setupRecyclerView()
        observeBookmarkedVerses()

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.rvBookmark.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvBookmark.adapter = bookmarkRVAdapter
    }

    private fun observeBookmarkedVerses() {
        Coroutines.main {
            viewModel.loadBookmarkedVerses().observe(viewLifecycleOwner) {
                bookmarkRVAdapter.submitList(it)
            }
        }
    }
}