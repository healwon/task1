package com.hurrypizza.test

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.hurrypizza.test.Gallery.Frag2_Adapter

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

        val imgs = arrayListOf<Int>(
            R.drawable.keith_haring_1,
            R.drawable.keith_haring_2,
            R.drawable.keith_haring_3,
            R.drawable.keith_haring_4,
            R.drawable.keith_haring_5,
            R.drawable.keith_haring_6,
            R.drawable.keith_haring_7
        )

        var adapter: Frag2_Adapter = Frag2_Adapter(requireContext(), imgs)

        gv.setAdapter(adapter)

        gv.setOnItemClickListener(object: AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                zoomFragment = SecondFragmentZoom()
                zoomFragment.imageIndex = position

                fragTransaction = fragManager.beginTransaction()
                fragTransaction.add(R.id.secondFragment, zoomFragment)
                fragTransaction.addToBackStack(null)
                fragTransaction.commit()
            }
        })
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