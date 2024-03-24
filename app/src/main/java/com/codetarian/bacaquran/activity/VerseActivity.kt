package com.codetarian.bacaquran.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.codetarian.bacaquran.adapter.ViewPagerAdapter
import com.codetarian.bacaquran.databinding.ActivityVerseBinding
import com.codetarian.bacaquran.domain.Surah
import com.codetarian.bacaquran.fragment.VerseFragment
import com.codetarian.bacaquran.viewmodel.SurahViewModel
import com.google.android.material.tabs.TabLayoutMediator

class VerseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerseBinding
    private lateinit var viewModel: SurahViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVerseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[SurahViewModel::class.java]

        viewModel.allSurah.observe(this) { list ->
            list?.let {
                setupViewPager(it)
                setupTabLayout(it)
            }
        }

        binding.toolbar.title = "Juz 1"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun setupTabLayout(list: List<Surah>) {
        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            tab.text = "${list[position].id}. ${list[position].transliteration}"
        }.attach()
    }

    private fun setupViewPager(list: List<Surah>) {
        val viewPagerAdapter by lazy {
            ViewPagerAdapter(supportFragmentManager, lifecycle).apply {
                createFragment(list.map { surah -> VerseFragment(surah) })
            }
        }
        val surahId = intent.getIntExtra(EXTRA_SURAH_ID, 1) - 1
        binding.apply {
            viewpager.adapter = viewPagerAdapter
            viewpager.post {
                tabs.setScrollPosition(surahId, 0f, true)
                tabs.getTabAt(surahId)?.select()
                viewpager.setCurrentItem(surahId, false)
            }
        }
    }

    companion object {
        const val EXTRA_SURAH_ID = "extra_surah_id"
    }
}