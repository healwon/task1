package com.hurrypizza.test

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter

class Frag2_Adapter(private val c: Context): PagerAdapter() {
    private lateinit var li: LayoutInflater

    val imgs = arrayOf(
        R.drawable.keith_haring_1,
        R.drawable.keith_haring_2,
        R.drawable.keith_haring_3
    )

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return imgs.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        li = c.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = li!!.inflate(R.layout.row, null)
        val iv = v.findViewById<View>(R.id.imageView) as ImageView
        iv.setImageResource(imgs[position])
        container.addView(v, 0)
        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.invalidate()
    }
}