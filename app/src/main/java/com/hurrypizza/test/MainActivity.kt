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
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.hurrypizza.test.Stopwatch.StopwatchService
import com.hurrypizza.test.Util.SwipeLockableViewPager

class MainActivity : AppCompatActivity() {

    private var firstFragment: FirstFragment? = null
    private var secondFragment: SecondFragment? = null
    private var thirdFragment: ThirdFragment? = null

    private var tabs_main: TabLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentAdapter = MyPagerAdapter(supportFragmentManager)
        val viewpager_main = findViewById<SwipeLockableViewPager>(R.id.viewpager_main)
        tabs_main = findViewById(R.id.tabs_main)
        viewpager_main.adapter = fragmentAdapter
        viewpager_main.setSwipePagingEnabled(false)


        firstFragment = fragmentAdapter.firstFragment
        secondFragment = fragmentAdapter.secondFragment
        thirdFragment = fragmentAdapter.thirdFragment

        tabs_main?.setupWithViewPager(viewpager_main)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            65637 -> { // 101+65536 (why??)
                if (grantResults.size > 0 && grantResults[0] == PERMISSION_GRANTED) {
                    Toast.makeText(this, "권한이 승인되었습니다.", Toast.LENGTH_SHORT).show()
                    firstFragment?.onPermissionGranted()
                }
            }
        }
    }

    override fun onBackPressed() {
        Log.d("mainActivity", "onBackPressed()")
        var index = tabs_main?.selectedTabPosition
        when (index) {
            0 -> if (firstFragment?.closeSearchView() == true) finish()
            1 -> if (supportFragmentManager.backStackEntryCount > 0) supportFragmentManager.popBackStack() else finish()
            else -> finish()
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
            0 -> "연락처"
            1 -> "갤러리"
            else-> {return "스톱워치"}
        }
    }
}