package com.hurrypizza.test

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import com.hurrypizza.test.Stopwatch.StopwatchService
import org.w3c.dom.Text
import java.util.*


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
/*
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
*/
    private var isRunning = false
    private var isNotZero = false
    private var timerTask: Timer? = null
    private var index :Int = 0

    private var tvCounter: TextView? = null
    private var layoutRecords: LinearLayout? = null
    private var btnLeft: Button? = null
    private var btnRight: Button? = null

    private lateinit var mService: StopwatchService
    private var mBound: Boolean = false

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d("fragment3", "onServiceConnected")
            val binder = service as StopwatchService.MyBinder
            mService = binder.getService()
            mBound = true

            isRunning = mService.getIsRunning()
            isNotZero = mService.getIsNotZero()
            if (isNotZero) {
                resume()
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d("fragment3", "onServiceDisconnected")
            mBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val viewOfLayout = inflater.inflate(R.layout.fragment_third, container, false)
/*
        viewStopWatchInit = inflater.inflate(R.layout.fragment_third_init, container, false)
        viewStopWatchTicking = inflater.inflate(R.layout.fragment_third_ticking, container, false)
        viewStopWatchPaused = inflater.inflate(R.layout.fragment_third_paused, container, false)


        fragManager = myContext.supportFragmentManager
        fragTransaction = fragManager.beginTransaction()

        initFragment = ThirdFragmentInit()
        tickingFragment = ThirdFragmentTicking()
        pausedFragment = ThirdFragmentPaused()

        fragTransaction.add(R.id.stopWatchFrame, initFragment)
        fragTransaction.commit()
*/
        tvCounter = viewOfLayout?.findViewById(R.id.tvCounter)
        layoutRecords = viewOfLayout?.findViewById(R.id.layoutRecords)
        btnLeft = viewOfLayout?.findViewById(R.id.btnLeft)
        btnRight = viewOfLayout?.findViewById(R.id.btnRight)

        btnLeft?.setOnClickListener {
            isRunning = !isRunning
            isNotZero = true
            if (isRunning) {
                btnLeft?.text = "Stop"
                btnRight?.text = "Record"
                start()
            } else {
                btnLeft?.text = "Start"
                btnRight?.text = "Reset"
                pause()
            }

        }

        btnRight?.setOnClickListener {
            if(isRunning) {
                val lapTime = mService.getTime()
                lapTime(lapTime)
            } else {

                reset()
            }
        }

        requireActivity().startService(Intent(requireContext(), StopwatchService::class.java))
        requireActivity().bindService(Intent(requireContext(), StopwatchService::class.java), connection, Context.BIND_AUTO_CREATE)
        return viewOfLayout
    }

    override fun onDestroy() {
        if (!isNotZero) {
            requireActivity().stopService(Intent(requireContext(), StopwatchService::class.java))
        }
        super.onDestroy()
    }

    private fun setTime() {
        val setTime = mService.getTime()
        var milli = setTime % 100
        var sec = (setTime/100) % 60
        var min = setTime / 6000
        tvCounter?.text = String.format("%02d:%02d.%02d",min,sec,milli)
    }

    fun dpToPx(context: Context, dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
    }

    private fun lapTime(lapTime: Int) {
        mService.addRecord(lapTime)
        val textView = TextView(requireContext()).apply {
            setTextSize(20f)
            gravity = Gravity.CENTER or Gravity.BOTTOM
            setPadding(dpToPx(requireContext(), 10f).toInt())
        }
        var milli = lapTime % 100
        var sec = (lapTime/100) % 60
        var min = lapTime / 6000
        textView.text = String.format("%02d:%02d.%02d",min,sec,milli)

        layoutRecords?.addView(textView,0)
        index++
    }

    private fun start() {
        mService.startAndPause()
        timerTask = kotlin.concurrent.timer(period = 10) {
            requireActivity().runOnUiThread {
                setTime()
            }
        }
    }

    private fun resume() {
        when (isRunning) {
            true -> {
                timerTask = kotlin.concurrent.timer(period = 10) {
                    requireActivity().runOnUiThread {
                        setTime()
                    }
                }
            }
            false -> {
                setTime()
            }
        }
        var size = mService.getRecordSize()
        for (i in 0 until size) {
            lapTime(mService.getRecord(i))
        }
    }

    private fun pause() {
        mService.startAndPause()
        timerTask?.cancel()
    }

    private fun reset() {
        mService.reset()
        timerTask?.cancel()

        isRunning = false
        isNotZero = false
        tvCounter?.text = "00:00.00"

        layoutRecords?.removeAllViews()
        index = 0
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