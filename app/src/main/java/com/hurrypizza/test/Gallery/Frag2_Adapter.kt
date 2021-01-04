package com.hurrypizza.test.Gallery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.hurrypizza.test.R

import uk.co.senab.photoview.PhotoViewAttacher

class Frag2_Adapter(private val c: Context, private var items: ArrayList<GalleryItem>): BaseAdapter() {
    private val context = c
    private val inf = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

//    override fun isViewFromObject(view: View, `object`: Any): Boolean {
//        return view == `object`
//    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        if (convertView == null) {
            val conView = inf.inflate(R.layout.row, null);
            val iv = conView.findViewById<ImageView>(R.id.imageView)
            val tv = conView.findViewById<TextView>(R.id.tv_gallery)
            setView(items[position], iv, tv)
            return conView
        } else {
            val conView = convertView
            val iv = conView.findViewById<ImageView>(R.id.imageView)
            val tv = conView.findViewById<TextView>(R.id.tv_gallery)
            setView(items[position], iv, tv)
            return conView
        }
    }

    fun setView(item: GalleryItem, iv:ImageView, tv:TextView) {
        iv.setImageResource(item.img)
        when (item.type) {
            1 -> {tv.text = item.dirName
            tv.setBackgroundResource(R.drawable.gallary_bg) }
            else -> {
                tv.text = ""
                tv.background = null
            }
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