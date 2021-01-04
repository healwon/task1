package com.hurrypizza.test

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.hurrypizza.test.Gallery.GalleryItem

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SecondFragmentNewFolder.newInstance] factory method to
 * create an instance of this fragment.
 */
class SecondFragmentNewFolder : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var viewOfLayout: View

    public lateinit var caller: SecondFragmentGallery
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

    private lateinit var myContext: FragmentActivity
    private lateinit var fragManager: FragmentManager
    private lateinit var fragTransaction: FragmentTransaction

    private lateinit var folderName: String

    private lateinit var inputText: EditText

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
        viewOfLayout = inflater.inflate(R.layout.fragment_second_new_folder, container, false)

        fragManager = myContext.supportFragmentManager

        var confirmButton = viewOfLayout.findViewById<Button>(R.id.confirmButton)
        confirmButton.setOnClickListener {
            inputText = viewOfLayout.findViewById(R.id.inputText)
            folderName = inputText.text.toString()
            if (folderName.length == 0) {return@setOnClickListener}
            var newGallery = SecondFragmentGallery()
            newGallery.parent = caller
            newGallery.items = items
            newGallery.dir_current = caller.dir_current.plus(folderName).plus("/")

            caller.directories.add(folderName)
            caller.children.add(newGallery)
            caller.items.add(0, GalleryItem(1, items[0].img, folderName))
            for (i in items) {
                caller.items.remove(i)
            }

            fragTransaction = fragManager.beginTransaction()
            fragTransaction.replace(R.id.secondFragment, newGallery)
            fragTransaction.commit()
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
         * @return A new instance of fragment SecondFragmentNewFolder.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SecondFragmentNewFolder().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}