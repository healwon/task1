package com.hurrypizza.test

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.hurrypizza.test.Gallery.GalleryItem
import uk.co.senab.photoview.PhotoView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SecondFragmentZoom.newInstance] factory method to
 * create an instance of this fragment.
 */
class SecondFragmentZoom : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var viewOfLayout: View
    private lateinit var myContext: FragmentActivity
    private lateinit var fragManager: FragmentManager

    var imageIndex: Int = 0
    var items: ArrayList<GalleryItem> = ArrayList<GalleryItem>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context as FragmentActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewOfLayout = inflater.inflate(R.layout.fragment_second_zoom, container, false)
        fragManager = myContext.supportFragmentManager

        val vp_gallery = viewOfLayout.findViewById<ViewPager>(R.id.vp_gallery)

        val imgItems = ArrayList<GalleryItem>()
        for (item in items) {
            when (item.type) {
                1 -> imageIndex--
                3 -> imageIndex--
                else -> imgItems.add(item)
            }
        }

        val adapter = galleryPagerAdapter(requireContext(), imgItems)
        vp_gallery.adapter = adapter
        vp_gallery.setCurrentItem(imageIndex, false)
        /*
        val image_current = img
        val imageView = viewOfLayout.findViewById<ImageView>(R.id.zoomImage)
        if (image_current != null) {
            imageView.setImageResource(image_current)
        } else {
            imageView.setImageResource(R.drawable.ic_outline_broken_image_24)
        }
        attacher = PhotoViewAttacher(imageView)
*/
        val exitButton = viewOfLayout.findViewById<ImageButton>(R.id.exitButton)
        exitButton.setOnClickListener{
            fragManager.popBackStack()
        }

        return viewOfLayout
    }

    override fun onResume() {
        super.onResume()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SecondFragmentZoom.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SecondFragmentZoom().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

class galleryPagerAdapter(val context: Context, val items: ArrayList<GalleryItem>): PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return (view == `object` as View)
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var rootview = inflater.inflate(R.layout.item_gallery, null)

        var imageView = rootview.findViewById<PhotoView>(R.id.zoomImage2)
        val item_current = items[position]
        Log.d("gvp","insntantiate")
        if (item_current != null) {
            when (item_current.type) {
                0 -> {
                    Glide.with(context)
                            .load(item_current.img)
                            .into(imageView)
                }
                2 -> {
                    Glide.with(context)
                            .load(item_current.bitmap)
                            .into(imageView)
                }

            }

        } else {
            imageView.setImageResource(R.drawable.ic_outline_broken_image_24)
        }

        val vp = container as ViewPager
        vp.addView(rootview)
        return rootview
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}