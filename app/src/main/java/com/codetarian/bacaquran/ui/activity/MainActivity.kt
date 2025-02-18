package com.codetarian.bacaquran.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.codetarian.bacaquran.R
import com.codetarian.bacaquran.databinding.ActivityMainBinding
import com.codetarian.bacaquran.ui.adapter.ViewPagerAdapter
import com.codetarian.bacaquran.ui.fragment.BookmarkFragment
import com.codetarian.bacaquran.ui.fragment.JuzFragment
import com.codetarian.bacaquran.ui.fragment.SurahFragment
import com.codetarian.bacaquran.utils.AssetUtil
import com.codetarian.bacaquran.utils.Coroutines
import com.codetarian.bacaquran.utils.constant.ModelConstants
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()
        setupTabLayout()

        Coroutines.io {
            copyModelsFromAssets()
            copyDatabaseFromAssets()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_find_surah -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupTabLayout() {
        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            val resId = when(position) {
                0 -> R.string.surah
                1 -> R.string.juz
                else -> R.string.bookmark
            }
            tab.setText(resId)
        }.attach()
    }

    private fun setupViewPager() {
        val viewPagerAdapter by lazy {
            ViewPagerAdapter(supportFragmentManager, lifecycle).apply {
                createFragment(SurahFragment())
                createFragment(JuzFragment())
                createFragment(BookmarkFragment())
            }
        }
        binding.viewpager.adapter = viewPagerAdapter
    }

    private fun copyDatabaseFromAssets() {
        val databaseFile = getDatabasePath("quran_database.db")
        try {
            val inputStream = assets.open("quran_database.db")
            val outputStream = FileOutputStream(databaseFile)
            inputStream.copyTo(outputStream)
            inputStream.close()
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun copyModelsFromAssets() {
        val folder = File("${getExternalFilesDir(null)}/models")
        if (!folder.exists()) {
            folder.mkdirs()
        }
        AssetUtil.copyAssets(
            this@MainActivity,
            arrayOf(ModelConstants.TFLITE_MODEL_FILENAME, ModelConstants.SCORER_FILENAME)
        )
    }
}