package com.hurrypizza.test

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
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
    internal lateinit var gv: GridView

    private lateinit var myContext: FragmentActivity
    private lateinit var fragManager: FragmentManager
    private lateinit var fragTransaction: FragmentTransaction

    private lateinit var zoomFragment: SecondFragmentZoom
    private lateinit var selectFragment: SecondFragmentSelect

    public var items = arrayListOf<Int>(
        R.drawable.keith_haring_1,
        R.drawable.keith_haring_2,
        R.drawable.keith_haring_3,
        R.drawable.keith_haring_4,
        R.drawable.keith_haring_5,
        R.drawable.keith_haring_6,
        R.drawable.keith_haring_7,
        R.drawable.pic_gif,
        R.drawable.pic_png,
        R.drawable.pic_0,
        R.drawable.pic_1,
        R.drawable.pic_2,
        R.drawable.pic_3,
        R.drawable.pic_4,
        R.drawable.pic_5,
        R.drawable.pic_6,
        R.drawable.pic_7,
        R.drawable.pic_8,
        R.drawable.pic_9,
    )
    var items: ArrayList<GalleryItem> = ArrayList<GalleryItem>()
    public var directories: MutableList<String> = mutableListOf()
    public var dir_current = "root/"

    public var parent: SecondFragmentGallery? = null
    public var children: MutableList<SecondFragmentGallery> = mutableListOf()

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
    ): View {
        // Inflate the layout for this fragment
        viewOfLayout = inflater.inflate(R.layout.fragment_second_gallery, container, false)

        fragManager = myContext.supportFragmentManager

        gv = viewOfLayout.findViewById(R.id.gridView) as GridView
        if (items.size == 0) {
            for (i in imgs) {
                items.add(GalleryItem(0, i, null))
            }
        }

        var adapter = Frag2_Adapter(myContext, items)

        gv.setAdapter(adapter)

        gv.setOnItemClickListener(object: AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (items[position].type) {
                    1 -> {
                        var i = directories.indexOf(items[position].dirName)
                        fragTransaction = fragManager.beginTransaction()
                        fragTransaction.replace(R.id.secondFragment, children[i])
                        fragTransaction.addToBackStack(null)
                        fragTransaction.commit()
                    }
                    0-> {
                        zoomFragment = SecondFragmentZoom()
                        zoomFragment.img = items[position].img
                        zoomFragment.imageIndex = position

                        fragTransaction = fragManager.beginTransaction()
                        fragTransaction.replace(R.id.secondFragment, zoomFragment)
                        fragTransaction.addToBackStack(null)
                        fragTransaction.commit()
                    }
                }
            }
        })

        var selectButton = viewOfLayout.findViewById<Button>(R.id.selectButton)
        selectButton.setOnClickListener{
            selectFragment = SecondFragmentSelect()
            selectFragment.caller = this
            selectFragment.items = items

            fragTransaction = fragManager.beginTransaction()
            fragTransaction.replace(R.id.secondFragment, selectFragment)
            fragTransaction.addToBackStack(null)
            fragTransaction.commit()
        }

        var dir_display = viewOfLayout.findViewById<TextView>(R.id.dir_display)
        dir_display.setText(dir_current)
/*
        var folderLinearLayout = viewOfLayout.findViewById<LinearLayout>(R.id.folderLayout)
        if (directories.size + 1 != folderLinearLayout.childCount) {
            children.forEachIndexed { i, child ->
                var newFolderText = TextView(myContext)
                newFolderText.setOnClickListener {
                    fragTransaction = fragManager.beginTransaction()
                    fragTransaction.replace(R.id.secondFragment, child)
                    fragTransaction.addToBackStack(null)
                    fragTransaction.commit()
                }
                newFolderText.setText(directories[i])
                folderLinearLayout.addView(newFolderText,
                    -1,
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    ))
            }
        }
*/
        return viewOfLayout
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