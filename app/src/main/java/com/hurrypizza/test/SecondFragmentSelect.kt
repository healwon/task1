package com.hurrypizza.test

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
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
 * Use the [SecondFragmentSelect.newInstance] factory method to
 * create an instance of this fragment.
 */
class SecondFragmentSelect : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var viewOfLayout: View
    internal lateinit var gv: GridView

    private lateinit var myContext: FragmentActivity
    private lateinit var fragManager: FragmentManager
    private lateinit var fragTransaction: FragmentTransaction

    private lateinit var newFolderFragment: SecondFragmentNewFolder
    private var selectedIndices = arrayListOf<Int>()

    public var imgs = arrayListOf<Int>(
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

    public lateinit var caller: SecondFragmentGallery

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
        viewOfLayout = inflater.inflate(R.layout.fragment_second_select, container, false)

        fragManager = myContext.supportFragmentManager

        gv = viewOfLayout.findViewById(R.id.selectGridView) as GridView

        var adapter = Frag2_Adapter(myContext, items)

        gv.setAdapter(adapter)

        gv.setOnItemClickListener(object: AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (selectedIndices.contains(position)){
                    selectedIndices.remove(position)
                    if (view != null) {
                        view.alpha = 1.0F
                    }
                } else {
                    selectedIndices.add(position)
                    if (view != null) {
                        val paint = Paint()
                        paint.setColor(Color.BLACK)
                        paint.alpha = 70
                        view.setBackgroundColor(paint.color)
                        view.alpha = 0.4F
                    }
                }

            }
        })

        var selectButton = viewOfLayout.findViewById<Button>(R.id.selectButton)
        selectButton.setOnClickListener{
            if (selectedIndices.size != 0) {
                newFolderFragment = SecondFragmentNewFolder()
                newFolderFragment.items = items.slice(selectedIndices) as ArrayList<GalleryItem>
                newFolderFragment.caller = caller

                fragTransaction = fragManager.beginTransaction()
                fragTransaction.replace(R.id.secondFragment, newFolderFragment)
                fragTransaction.commit()
            }
        }

        return viewOfLayout
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SecondFragmentSelect.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SecondFragmentSelect().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}