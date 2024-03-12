package com.codetarian.bacaquran.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codetarian.bacaquran.adapter.ViewPagerAdapter
import com.codetarian.bacaquran.fragment.SurahFragment
import com.codetarian.bacaquran.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
}