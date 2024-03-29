package com.codetarian.bacaquran.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.codetarian.bacaquran.adapter.ViewPagerAdapter
import com.codetarian.bacaquran.databinding.ActivityVerseBinding
import com.codetarian.bacaquran.domain.Surah
import com.codetarian.bacaquran.fragment.VerseFragment
import com.codetarian.bacaquran.fragment.VerseFragment.VerseFragmentListener
import com.codetarian.bacaquran.viewmodel.SurahViewModel
import com.google.android.material.tabs.TabLayoutMediator

class VerseActivity : AppCompatActivity(), VerseFragmentListener {

    private lateinit var binding: ActivityVerseBinding
    private lateinit var viewModel: SurahViewModel
    private lateinit var listSurah: List<Surah>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVerseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = null

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[SurahViewModel::class.java]

        viewModel.allSurah.observe(this) { list ->
            list?.let {
                listSurah = it
                setupViewPager()
                setupTabLayout()
            }
        }
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
                supportActionBar?.title = "Juz ${listSurah[position].firstJuz}"
            }
        })
    }

    private fun setupTabLayout() {
        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            tab.text = "${listSurah[position].id}. ${listSurah[position].transliteration}"
        }.attach()
    }

    private fun setupViewPager() {
        val viewPagerAdapter by lazy {
            ViewPagerAdapter(supportFragmentManager, lifecycle).apply {
                createFragment(listSurah.map { surah -> VerseFragment(surah) })
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