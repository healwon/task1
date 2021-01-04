package com.hurrypizza.test.Gallery

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hurrypizza.test.Contact.ContactItem
import com.hurrypizza.test.R

import uk.co.senab.photoview.PhotoViewAttacher

class Frag2_Adapter(val c: Context, var items: ArrayList<GalleryItem>): RecyclerView.Adapter<Frag2_Adapter.Holder>() {
    private val context = c
    private val inf = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var mListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(v: View, pos: Int)
    }

    inner class inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val iv = itemView?.findViewById<ImageView>(R.id.imageView)
        val tv = itemView?.findViewById<TextView>(R.id.tv_gallery)

        fun bind(item: GalleryItem, context: Context) {
            Log.d("adapter","")
            if (item.type < 2) { // not imported
                iv?.setImageResource(item.img!!)
                when (item.type) {
                    1 -> {tv?.text = item.dirName
                        tv?.setBackgroundResource(R.drawable.gallary_bg) }
                    else -> {
                        tv?.text = ""
                        tv?.background = null
                    }
                }
            } else { // imported
                iv?.setImageBitmap(item.bitmap)
                when (item.type) {
                    3 -> {
                        tv?.text = item.dirName
                        tv?.setBackgroundResource(R.drawable.gallary_bg)
                    }
                    else -> {
                        tv?.text = ""
                        tv?.background = null
                    }
                }
            }

        }
    }
/*
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
        Glide.with(context)
            .load(item.img)
            .into(iv)
        when (item.type) {
            1 -> {tv.text = item.dirName
            tv.setBackgroundResource(R.drawable.gallary_bg) }
            else -> {
                tv.text = ""
                tv.background = null
            }
        }
    }
*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
    val view = LayoutInflater.from(context).inflate(R.layout.row, parent, false)
    return Holder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position], context)
        holder.itemView.setOnClickListener {
            if (position != RecyclerView.NO_POSITION) {
                if (mListener != null) {
                    mListener!!.onItemClick(holder.itemView, position)
                }
            }
        }
    }

    fun setOnItemClickListener(onItemClick: (v: View, pos: Int)-> Unit) {
        mListener = object: OnItemClickListener {
            override fun onItemClick(v: View, pos: Int) {
                onItemClick(v, pos)
            }
        }
    }
}