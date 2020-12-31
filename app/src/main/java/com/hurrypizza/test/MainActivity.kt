package com.hurrypizza.test

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.hurrypizza.test.Stopwatch.StopwatchService

class MainActivity : AppCompatActivity() {

    private var firstFragment: FirstFragment? = null
    private var secondFragment: SecondFragment? = null
    private var thirdFragment: ThirdFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentAdapter = MyPagerAdapter(supportFragmentManager)
        val viewpager_main = findViewById<ViewPager>(R.id.viewpager_main)
        val tabs_main = findViewById<TabLayout>(R.id.tabs_main)
        viewpager_main.adapter = fragmentAdapter

        firstFragment = fragmentAdapter.firstFragment
        secondFragment = fragmentAdapter.secondFragment
        thirdFragment = fragmentAdapter.thirdFragment

        tabs_main.setupWithViewPager(viewpager_main)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            65637 -> { // 101+65536 (why??)
                if (grantResults.size > 0 && grantResults[0] == PERMISSION_GRANTED) {
                    Toast.makeText(this, "권한이 승인되었습니다. 앱을 다시 시작해 주세요.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

class MyPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    var firstFragment: FirstFragment = FirstFragment()
    var secondFragment: SecondFragment = SecondFragment()
    var thirdFragment: ThirdFragment = ThirdFragment()

    override fun getItem(poisition: Int): Fragment {
        return when (poisition) {
            0 -> {firstFragment}
            1 -> {secondFragment}
            else -> {thirdFragment}
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