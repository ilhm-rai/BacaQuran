package com.codetarian.bacaquran.activity

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.codetarian.bacaquran.R
import com.codetarian.bacaquran.adapter.ViewPagerAdapter
import com.codetarian.bacaquran.databinding.ActivityVerseBinding
import com.codetarian.bacaquran.domain.Surah
import com.codetarian.bacaquran.fragment.VerseFragment
import com.codetarian.bacaquran.viewmodel.SurahViewModel
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.android.material.tabs.TabLayoutMediator

class VerseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerseBinding
    private lateinit var viewModel: SurahViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[SurahViewModel::class.java]

        val allSurah = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableArrayListExtra(EXTRA_ALL_SURAH, Surah::class.java)
        } else {
            val mapper = ObjectMapper()
            val jsonAllSurah = intent.getStringExtra(EXTRA_ALL_SURAH)
            mapper.readValue(jsonAllSurah, object: TypeReference<ArrayList<Surah>>(){})
        }

        binding.apply {
            setSupportActionBar(toolbar)
            toolbar.apply {
                title = "Juz 1"
                navigationIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_action_back, null)
            }
            allSurah?.let {
                addTabsToViewPager(it)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun addTabsToViewPager(list: List<Surah>) {
        val surahItem = intent.getIntExtra(EXTRA_SURAH_ID, 1) - 1
        val viewPagerAdapter by lazy {
            ViewPagerAdapter(supportFragmentManager, lifecycle).apply {
                createFragment(list.map { surah -> VerseFragment(surah) })
            }
        }
        binding.apply {
            viewpager.apply {
                adapter = viewPagerAdapter
            }
            TabLayoutMediator(tabs, viewpager) { tab, position ->
                tab.text = list[position].transliteration
            }.attach()
            viewpager.post {
                viewpager.setCurrentItem(surahItem, false)
                tabs.setScrollPosition(surahItem, 0f, true)
                tabs.getTabAt(surahItem)?.select()
            }
        }
    }

    companion object {
        const val EXTRA_SURAH_ID = "extra_surah_id"
        const val EXTRA_ALL_SURAH = "extra_all_surah"
    }
}