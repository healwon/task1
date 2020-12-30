package com.hurrypizza.test

import android.content.Context
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(val context: Context, val items: ArrayList<ContactItem>): RecyclerView.Adapter<ContactAdapter.Holder>() {

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val img = itemView?.findViewById<ImageView>(R.id.contact_img)
        val name = itemView?.findViewById<TextView>(R.id.contact_name)
        val number = itemView?.findViewById<TextView>(R.id.contact_number)

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun bind (contact: ContactItem, context: Context) {
            img?.clipToOutline = true
            name?.text = contact.name
            number?.text = contact.number
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position], context)
    }

}