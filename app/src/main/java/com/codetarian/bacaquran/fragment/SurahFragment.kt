package com.codetarian.bacaquran.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.codetarian.bacaquran.adapter.SurahRVAdapter
import com.codetarian.bacaquran.adapter.SurahClickInterface
import com.codetarian.bacaquran.viewmodel.SurahViewModel
import com.codetarian.bacaquran.databinding.FragmentSurahBinding
import com.codetarian.bacaquran.domain.SurahDomain

class SurahFragment : Fragment(), SurahClickInterface {

    private lateinit var binding: FragmentSurahBinding
    private lateinit var viewModel: SurahViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSurahBinding.inflate(inflater, container, false)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val surahRVAdapter by lazy { SurahRVAdapter(this.requireContext(), this) }
        
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[SurahViewModel::class.java]

        viewModel.allSurah.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                surahRVAdapter.updateList(it)
            }
        })

        binding.apply {
            recyclerview.apply {
                adapter = surahRVAdapter
                this.layoutManager = layoutManager
                addItemDecoration(
                    DividerItemDecoration(
                        this.context,
                        layoutManager.orientation
                    )
                )
            }
        }

        return binding.recyclerview
    }

    override fun onSurahClick(surah: SurahDomain) {
        TODO("Not yet implemented")
    }
}