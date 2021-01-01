package com.hurrypizza.test

import android.content.Context
import android.graphics.drawable.Drawable
import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import uk.co.senab.photoview.PhotoViewAttacher

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

    private lateinit var galleryFragment: SecondFragmentGallery

    private lateinit var attacher: PhotoViewAttacher

    public var imageIndex: Int = 0

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

        val imgs = arrayListOf<Int>(
            R.drawable.keith_haring_1,
            R.drawable.keith_haring_2,
            R.drawable.keith_haring_3,
            R.drawable.keith_haring_4,
            R.drawable.keith_haring_5,
            R.drawable.keith_haring_6,
            R.drawable.keith_haring_7
        )
        val image_current = imgs[imageIndex]
        val imageView = viewOfLayout.findViewById<ImageView>(R.id.zoomImage)
        imageView.setImageResource(image_current)
        attacher = PhotoViewAttacher(imageView)

        galleryFragment = SecondFragmentGallery()

        val exitButton = viewOfLayout.findViewById<ImageButton>(R.id.exitButton)
        exitButton.setOnClickListener{
            fragManager.popBackStack()
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