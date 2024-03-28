package com.codetarian.bacaquran.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.codetarian.bacaquran.adapter.ViewPagerAdapter
import com.codetarian.bacaquran.constant.ModelConstants
import com.codetarian.bacaquran.databinding.ActivitySurahBinding
import com.codetarian.bacaquran.fragment.SurahFragment
import com.codetarian.bacaquran.util.AssetUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class SurahActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySurahBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySurahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()

        CoroutineScope(Dispatchers.IO).launch {
            copyModelsFromAssets()
            copyDatabaseFromAssets()
        }
    }

    private fun setupViewPager() {
        val viewPagerAdapter by lazy {
            ViewPagerAdapter(supportFragmentManager, lifecycle).apply {
                createFragment(SurahFragment())
            }
        }
        binding.viewpager.adapter = viewPagerAdapter
    }

    private fun copyDatabaseFromAssets() {
        val databaseFile = getDatabasePath("quran_database.db")
        if (!databaseFile.exists()) {
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
    }

    private fun copyModelsFromAssets() {
        val folder = File("${getExternalFilesDir(null)}/models")
        if (!folder.exists()) {
            folder.mkdirs()
        }
        AssetUtil.copyAssets(
            this@SurahActivity,
            arrayOf(ModelConstants.TFLITE_MODEL_FILENAME, ModelConstants.SCORER_FILENAME)
        )
    }
}