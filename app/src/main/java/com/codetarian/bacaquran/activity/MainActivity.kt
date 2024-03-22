package com.codetarian.bacaquran.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.codetarian.bacaquran.adapter.ViewPagerAdapter
import com.codetarian.bacaquran.databinding.ActivityMainBinding
import com.codetarian.bacaquran.fragment.SurahFragment
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        copyDatabaseFromAssets()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            val viewPagerAdapter by lazy { ViewPagerAdapter(supportFragmentManager, lifecycle).apply {
                createFragment(SurahFragment())
            } }
            viewpager.apply {
                adapter = viewPagerAdapter
            }
        }
    }

    private fun copyDatabaseFromAssets() {
        val databaseFile = getDatabasePath("quran_database.db")
//        if (!databaseFile.exists()) {
        try {
            val inputStream = assets.open("quran_database.db")
            val outputStream = FileOutputStream(databaseFile)
            inputStream.copyTo(outputStream)
            inputStream.close()
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
//        }
    }
}