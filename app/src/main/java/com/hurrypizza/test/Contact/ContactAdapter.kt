package com.hurrypizza.test.Contact

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.hurrypizza.test.R
import kotlin.collections.ArrayList

class ContactAdapter(val context: Context, val items: ArrayList<ContactItem>): RecyclerView.Adapter<ContactAdapter.Holder>(), Filterable {

    private var displayItems: ArrayList<ContactItem> = items

    inner class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val img = itemView?.findViewById<ImageView>(R.id.contact_img)
        val name = itemView?.findViewById<TextView>(R.id.contact_name)
        val number = itemView?.findViewById<TextView>(R.id.contact_number)

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun bind (contact: ContactItem, context: Context) {
            Log.d("adabter","")
            if (contact.thumb == null) {
                img?.setImageResource(R.drawable.ic_baseline_account_circle_24)

                val contactIconColors = context.resources.getIntArray(R.array.contactIconColors)
                val i = contact.defaultThumb
                img?.background?.setTint(contactIconColors[i])
            } else {
                img?.setImageURI(Uri.parse(contact.thumb))
            }
            img?.clipToOutline = true
            name?.text = contact.name
            number?.text = contact.number
        }
    }

    init {
        displayItems = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false)
        return Holder(view)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(displayItems[position], context)
        holder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(ContactsContract.Contacts.CONTENT_URI.toString()+"/"+displayItems[position].id))
            startActivity(context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return displayItems.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val str = constraint.toString().toLowerCase()
                if (str.isEmpty()) {
                    displayItems = items
                } else {
                    val filteredItems = ArrayList<ContactItem>()
                    for (row in items) {
                        if (row.name.toLowerCase().contains(str)) {
                            filteredItems.add(row)
                            continue
                        }
                        val str_num = str.replace(("[^\\d.]").toRegex(), "")
                        if (str_num.length != 0) {
                            if (row.number.replace(("[^\\d.]").toRegex(), "").contains(str_num)) filteredItems.add(row)
                        }
                    }
                    displayItems = filteredItems
                }
                val filterResults = FilterResults()
                filterResults.values = displayItems
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                notifyDataSetChanged()
            }
        }
    }
}