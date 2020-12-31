package com.hurrypizza.test.Gallery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.hurrypizza.test.R

class Frag2_Adapter(private val c: Context, layout: Int): BaseAdapter() {
    private val context = c
    val layout = layout
    private val inf = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    val imgs = arrayOf(
            R.drawable.keith_haring_1,
            R.drawable.keith_haring_2,
            R.drawable.keith_haring_3,
            R.drawable.keith_haring_4,
            R.drawable.keith_haring_5,
            R.drawable.keith_haring_6,
            R.drawable.keith_haring_7
    )

//    override fun isViewFromObject(view: View, `object`: Any): Boolean {
//        return view == `object`
//    }

    override fun getCount(): Int {
        return imgs.size
    }

    override fun getItem(position: Int): Any {
        return imgs[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        if (convertView == null) {
            val conView = inf.inflate(layout, null);
            val iv = conView.findViewById<ImageView>(R.id.imageView) as ImageView
            iv.setImageResource(imgs[position])
            return conView
        } else {
            val conView = convertView
            val iv = conView.findViewById<ImageView>(R.id.imageView) as ImageView
            iv.setImageResource(imgs[position])
            return conView
        }
    }
//    override fun instantiateItem(container: ViewGroup, position: Int): Any {
//        li = c.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val v = li!!.inflate(R.layout.row, null)
//        val iv = v.findViewById<View>(R.id.imageView) as ImageView
//        iv.setImageResource(imgs[position])
//        container.addView(v, 0)
//        return v
//    }

//    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//        container.invalidate()
//    }
}