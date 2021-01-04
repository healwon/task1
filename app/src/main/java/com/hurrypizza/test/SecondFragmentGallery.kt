package com.hurrypizza.test

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hurrypizza.test.Gallery.Frag2_Adapter
import com.hurrypizza.test.Gallery.GalleryItem

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SecondFragmentGallery.newInstance] factory method to
 * create an instance of this fragment.
 */
class SecondFragmentGallery : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var viewOfLayout: View
    internal lateinit var gv: RecyclerView
    private lateinit var dir_display: TextView

    private lateinit var myContext: FragmentActivity
    private lateinit var fragManager: FragmentManager
    private lateinit var fragTransaction: FragmentTransaction

    private lateinit var zoomFragment: SecondFragmentZoom
    private lateinit var selectFragment: SecondFragmentSelect

    var spanCount: Int = 2

    var imgs = arrayListOf<Int>(
        R.raw.keith_haring_1,
        R.raw.keith_haring_2,
        R.raw.keith_haring_3,
        R.raw.keith_haring_4,
        R.raw.keith_haring_5,
        R.raw.keith_haring_6,
        R.raw.keith_haring_7,
        R.raw.pic_gif,
        R.raw.pic_png,
        R.raw.pic_0,
        R.raw.pic_1,
        R.raw.pic_2,
        R.raw.pic_3,
        R.raw.pic_4,
        R.raw.pic_5,
        R.raw.pic_6,
        R.raw.pic_7,
        R.raw.pic_8,
        R.raw.pic_9,
    )
    var items: ArrayList<GalleryItem> = ArrayList<GalleryItem>()
    var dir_current = "root/"

    var parent: SecondFragmentGallery? = null

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
        if (items.size == 0) {
            for (i in imgs) {
                items.add(GalleryItem(0, i, null, null))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewOfLayout = inflater.inflate(R.layout.fragment_second_gallery, container, false)

        fragManager = myContext.supportFragmentManager

        gv = viewOfLayout.findViewById(R.id.gridView)

        var adapter = Frag2_Adapter(myContext, items)
        adapter.setOnItemClickListener { v, pos ->
            when (items[pos].type) {
                1 -> {
                    items[pos].frag!!.dir_current =
                        dir_current.plus(items[pos].dirName).plus("/")
                    //var i = directories.indexOf(items[position].dirName)
                    fragTransaction = fragManager.beginTransaction()
                    fragTransaction.replace(R.id.secondFragment, items[pos].frag!!)
                    fragTransaction.addToBackStack(null)
                    fragTransaction.commit()
                }
                0 -> {
                    zoomFragment = SecondFragmentZoom()
                    zoomFragment.items = ArrayList(items)
                    zoomFragment.imageIndex = pos

                    fragTransaction = fragManager.beginTransaction()
                    fragTransaction.replace(R.id.secondFragment, zoomFragment)
                    fragTransaction.addToBackStack(null)
                    fragTransaction.commit()
                }
            }
        }
        gv.setAdapter(adapter)

        val gm = GridLayoutManager(requireContext(), spanCount)
        gv.layoutManager = gm

        val spacing: Int = getResources().getDimensionPixelSize(R.dimen.recycler_spacing);
        gv.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.left = spacing;
                outRect.right = spacing;
                outRect.bottom = spacing;
                outRect.top = spacing;
            }
        })
        gv.setHasFixedSize(true)

        // credit:: by 박해철: begin
        var scaleFactor: Float = 0F
        val gestureDetector = ScaleGestureDetector(requireContext(), object: ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector?): Boolean {
                scaleFactor *= detector!!.scaleFactor
//                scaleFactor = if (scaleFactor < 1) 1F else scaleFactor // prevent our view from becoming too small //
//                scaleFactor = ((scaleFactor * 100) as Int).toFloat() / 100 // Change precision to help with jitter when user just rests their fingers //
                if (scaleFactor > 1.5F) {
                    if (spanCount > 2) {
                        spanCount--
                        scaleFactor = 0F
                    }
                } else if (scaleFactor > 0F && scaleFactor < 0.7F) {
                    if (spanCount < 4) {
                        spanCount++
                        scaleFactor = 0F
                    }
                }
                gm.spanCount = spanCount
                return super.onScale(detector)
            }
        })

        (activity as MainActivity).registerMyOnTouchListener(object : MainActivity.MyOnTouchListener{
            override fun OnTouch(ev: MotionEvent?) {
                if (ev?.action == MotionEvent.ACTION_DOWN) {
                    scaleFactor = 1F
                }
                gestureDetector?.onTouchEvent(ev)
            }
        })
        // credit:: by 박해철: end
        var this_frag = this
        gv.isLongClickable = true

        /*
        adapter.setOnItemLongClickListener(object: AdapterView.OnItemLongClickListener{
            override fun onItemLongClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ): Boolean {
                selectFragment = SecondFragmentSelect()
                selectFragment.caller = this_frag
                selectFragment.items = items
                selectFragment.initially_selected = position

                fragTransaction = fragManager.beginTransaction()
                fragTransaction.replace(R.id.secondFragment, selectFragment)
                fragTransaction.addToBackStack(null)
                fragTransaction.commit()
                return true
            }
        })
*/
        var selectButton = viewOfLayout.findViewById<Button>(R.id.selectButton)
        selectButton.setOnClickListener{
            selectFragment = SecondFragmentSelect()
            selectFragment.caller = this
            selectFragment.items = items
            selectFragment.spanCount = spanCount

            fragTransaction = fragManager.beginTransaction()
            fragTransaction.replace(R.id.secondFragment, selectFragment)
            fragTransaction.addToBackStack(null)
            fragTransaction.commit()
        }

        dir_display = viewOfLayout.findViewById<TextView>(R.id.dir_display)
        dir_display.setText(dir_current)

        return viewOfLayout
    }

    override fun onResume() {
        Log.d("secondFragmentGallery", "onResume()")
        dir_display.text = dir_current
        for (item in items) {
            if (item.type == 1) {
                when (item.frag!!.items.size) {
                    0 -> {
                        item.img = R.drawable.ic_outline_broken_image_24
                    }
                    else -> {
                        item.img = item.frag!!.items[0].img
                    }
                }
            }
        }
        items.sortWith(compareBy({1-it.type},{it.dirName}))
        super.onResume()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SecondFragment.
         */
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SecondFragment.
         */

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SecondFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}