package com.hurrypizza.test

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentAdapter = MyPagerAdapter(supportFragmentManager)
        val viewpager_main = findViewById<ViewPager>(R.id.viewpager_main)
        val tabs_main = findViewById<TabLayout>(R.id.tabs_main)
        viewpager_main.adapter = fragmentAdapter

        tabs_main.setupWithViewPager(viewpager_main)
    }
}

class MyPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
    override fun getItem(poisition: Int): Fragment {
        return when (poisition) {
            0 -> {FirstFragment()}
            1 -> {SecondFragment()}
            else -> {ThirdFragment()}
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "첫번째꺼"
            1 -> "두번째꺼"
            else-> {return "세번째꺼"}
        }
    }
}