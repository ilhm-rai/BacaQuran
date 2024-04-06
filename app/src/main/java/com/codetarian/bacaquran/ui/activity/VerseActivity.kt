package com.codetarian.bacaquran.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.viewpager2.widget.ViewPager2
import com.codetarian.bacaquran.databinding.ActivityVerseBinding
import com.codetarian.bacaquran.ui.adapter.VersePagerAdapter
import com.codetarian.bacaquran.ui.fragment.VerseFragment.VerseFragmentListener
import com.codetarian.bacaquran.ui.viewmodel.QuranViewModel
import com.codetarian.bacaquran.utils.Coroutines
import com.codetarian.bacaquran.utils.constant.StringConstants
import com.google.android.material.tabs.TabLayoutMediator

class VerseActivity : AppCompatActivity(), VerseFragmentListener {

    private val viewModel by viewModels<QuranViewModel>()
    private lateinit var binding: ActivityVerseBinding
    private lateinit var versePagerAdapter: VersePagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVerseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = null

        versePagerAdapter = VersePagerAdapter(supportFragmentManager, lifecycle)
        binding.viewpager.adapter = versePagerAdapter

        setupTabLayout()
        setupVerseViewPager()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override fun onTitleChanged(title: String) {
        supportActionBar?.title = title
    }

    override fun onPageChange(nestedScrollView: NestedScrollView) {
        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                nestedScrollView.scrollTo(0, 0)
                supportActionBar?.title = String.format(StringConstants.JUZ_TITLE, versePagerAdapter.getSurah(position).firstJuz)
            }
        })
    }

    private fun setupTabLayout() {
        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            tab.text = "${versePagerAdapter.getSurah(position).id}. ${versePagerAdapter.getSurah(position).transliteration}"
        }.attach()
    }

    private fun setupVerseViewPager() {
        Coroutines.main {
            viewModel.loadSurahData().observe(this@VerseActivity) { surahList ->
                versePagerAdapter.submitList(surahList)
                val position = intent.getIntExtra(EXTRA_SURAH_ID, 1) - 1
                supportActionBar?.title = "Juz ${surahList[position].firstJuz}"
                binding.viewpager.setCurrentItem(position, false)
                binding.tabs.setScrollPosition(position, 0f, true)
            }
        }
    }

    companion object {
        const val EXTRA_SURAH_ID = "extra_surah_id"
    }
}