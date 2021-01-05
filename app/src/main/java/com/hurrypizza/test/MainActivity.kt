package com.hurrypizza.test

import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.hurrypizza.test.Util.SwipeLockableViewPager

class MainActivity : AppCompatActivity() {

    val PERMISSION_READ_CONTACT: Int = 101

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

        val tabIndex = intent.extras?.getInt("tabIndex", 0)
        if (tabIndex != null) {
            tabs_main?.getTabAt(tabIndex)?.select()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_READ_CONTACT+65636 -> { // 101+65536 (why??)
                if (grantResults.size > 0 && grantResults[0] == PERMISSION_GRANTED) {
                    Toast.makeText(this, "권한이 승인되었습니다.", Toast.LENGTH_SHORT).show()
                    firstFragment?.onPermissionGranted()
                }
            }
        }
    }

    override fun onBackPressed() {
        val index = tabs_main?.selectedTabPosition
        when (index) {
            0 -> if (firstFragment?.closeSearchView() == true) finish()
            1 -> if (supportFragmentManager.backStackEntryCount > 0) supportFragmentManager.popBackStack() else finish()
            else -> finish()
        }
    }
    // #begin: src by 박해철
    /**
     * This code is used for gesture detection in second tab (SecondFragmentGallery.kt)
     */
    private val OnTouchListener= ArrayList<MyOnTouchListener>()
    interface MyOnTouchListener{
        fun OnTouch(ev: MotionEvent?)
    }
    fun registerMyOnTouchListener(listener: MyOnTouchListener){
        OnTouchListener.add(listener)
    }
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        for (listener in OnTouchListener) listener.OnTouch(ev)
        return super.dispatchTouchEvent(ev)
    }
    // #end: src by 박해철
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