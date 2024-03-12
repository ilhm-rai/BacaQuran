package com.codetarian.bacaquran.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.codetarian.bacaquran.adapter.SurahAdapter
import com.codetarian.bacaquran.viewmodel.SurahViewModel
import com.codetarian.bacaquran.databinding.FragmentSurahBinding

class SurahFragment : Fragment() {

    private lateinit var binding: FragmentSurahBinding
    private val surahViewModel: SurahViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSurahBinding.inflate(inflater, container, false)
        val layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        binding.apply {
            val surahAdapter by lazy { SurahAdapter(surahViewModel.loadSurah()) }
            recyclerview.apply {
                adapter = surahAdapter
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
}