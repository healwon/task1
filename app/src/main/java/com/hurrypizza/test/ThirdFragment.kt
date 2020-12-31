package com.hurrypizza.test

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ThirdFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ThirdFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var viewOfLayout: View
    private lateinit var viewStopWatchInit: View
    private lateinit var viewStopWatchTicking: View
    private lateinit var viewStopWatchPaused: View

    private lateinit var myContext: FragmentActivity
    private lateinit var fragManager: FragmentManager
    private lateinit var fragTransaction: FragmentTransaction

    private lateinit var initFragment: ThirdFragmentInit
    private lateinit var tickingFragment: ThirdFragmentTicking
    private lateinit var pausedFragment: ThirdFragmentPaused

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

        viewStopWatchInit = inflater.inflate(R.layout.fragment_third_init, container, false)
        viewStopWatchTicking = inflater.inflate(R.layout.fragment_third_ticking, container, false)
        viewStopWatchPaused = inflater.inflate(R.layout.fragment_third_paused, container, false)
        viewOfLayout = inflater.inflate(R.layout.fragment_third, container, false)

        fragManager = myContext.supportFragmentManager
        fragTransaction = fragManager.beginTransaction()

        initFragment = ThirdFragmentInit()
        tickingFragment = ThirdFragmentTicking()
        pausedFragment = ThirdFragmentPaused()

        fragTransaction.add(R.id.stopWatchFrame, initFragment)
        fragTransaction.commit()

        val startButton = viewStopWatchInit.findViewById<Button>(R.id.btnStart) as Button
        val stopButton = viewStopWatchTicking.findViewById<Button>(R.id.btnStop) as Button
        val recordButton = viewStopWatchTicking.findViewById<Button>(R.id.btnRecord) as Button
        val contButton = viewStopWatchPaused.findViewById<Button>(R.id.btnCont) as Button
        val resetButton = viewStopWatchPaused.findViewById<Button>(R.id.btnReset) as Button



        return viewOfLayout
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ThirdFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ThirdFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

class StopwatchAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
    override fun getItem(poisition: Int): Fragment {
        return when (poisition) {
            0 -> {ThirdFragmentInit()}
            1 -> {ThirdFragmentTicking()}
            else -> {ThirdFragmentPaused()}
        }
    }

    override fun getCount(): Int {
        return 3
    }
}