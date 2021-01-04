package com.hurrypizza.test

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SecondFragmentSetDirDest.newInstance] factory method to
 * create an instance of this fragment.
 */
class SecondFragmentSetDirDest : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var myContext: FragmentActivity
    private lateinit var fragManager: FragmentManager
    private lateinit var fragTransaction: FragmentTransaction

    private lateinit var viewOfLayout: View
    public lateinit var caller: SecondFragmentGallery
    public var copy_mode = true
    public var items = arrayListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context as FragmentActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewOfLayout = inflater.inflate(R.layout.fragment_second_set_dir_dest, container, false)
        fragManager = myContext.supportFragmentManager

        var selectDestLayout = viewOfLayout.findViewById<LinearLayout>(R.id.selectDestLayout)

        if (copy_mode) {
            if(caller.parent != null) {
                var parentView = TextView(myContext)
                parentView.text = ".."
                parentView.textSize = 30.0F
                parentView.gravity = Gravity.CENTER
                parentView.setTextColor(Color.BLACK)
                parentView.setOnClickListener {
                    caller.parent!!.items = items.plus(caller.parent!!.items) as ArrayList<Int>

                    fragTransaction = fragManager.beginTransaction()
                    fragTransaction.replace(R.id.secondFragment, caller.parent!!)
                    fragTransaction.commit()
                }
                selectDestLayout.addView(parentView, -1, ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ))
            }
            caller.children.forEachIndexed { index, child ->
                var childView = TextView(myContext)
                childView.text = caller.directories[index]
                childView.textSize = 30.0F
                childView.gravity = Gravity.CENTER
                childView.setTextColor(Color.BLACK)
                childView.setOnClickListener{
                    child.items = items.plus(child.items) as ArrayList<Int>
                    fragTransaction = fragManager.beginTransaction()
                    fragTransaction.replace(R.id.secondFragment, child)
                    fragTransaction.commit()
                }
                selectDestLayout.addView(childView, -1, ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ))
            }

        } else {
            if(caller.parent != null) {
                var parentView = TextView(myContext)
                parentView.text = ".."
                parentView.textSize = 30.0F
                parentView.gravity = Gravity.CENTER
                parentView.setTextColor(Color.BLACK)
                parentView.setOnClickListener {
                    caller.parent!!.items = items.plus(caller.parent!!.items) as ArrayList<Int>
                    for (img in items) {
                        caller.items.remove(img)
                    }

                    fragTransaction = fragManager.beginTransaction()
                    fragTransaction.replace(R.id.secondFragment, caller.parent!!)
                    fragTransaction.commit()
                }
                selectDestLayout.addView(parentView, -1, ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ))
            }

            caller.children.forEachIndexed { index, child ->
                var childView = TextView(myContext)
                childView.text = caller.directories[index]
                childView.textSize = 30.0F
                childView.gravity = Gravity.CENTER
                childView.setTextColor(Color.BLACK)
                childView.setOnClickListener{
                    child.items = items.plus(child.items) as ArrayList<Int>
                    for (img in items) {
                        caller.items.remove(img)
                    }

                    fragTransaction = fragManager.beginTransaction()
                    fragTransaction.replace(R.id.secondFragment, child)
                    fragTransaction.commit()
                }
                selectDestLayout.addView(childView, -1, ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ))
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
         * @return A new instance of fragment SecondFragmentSetDirDest.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SecondFragmentSetDirDest().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}