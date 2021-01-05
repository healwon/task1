package com.hurrypizza.test.Gallery

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.hurrypizza.test.R
import com.hurrypizza.test.Gallery.SecondFragmentGallery
import java.io.FileDescriptor

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SecondFragmentImport.newInstance] factory method to
 * create an instance of this fragment.
 */
class SecondFragmentImport : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val READ_REQUEST_CODE = 1000

    // for fragment switching
    private lateinit var myContext: FragmentActivity
    private lateinit var fragManager: FragmentManager

    public lateinit var caller: SecondFragmentGallery

    private lateinit var bitmap: Bitmap
    private lateinit var viewOfLayout: View

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

        // Try to get some image files
        val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
            }
        } else {
            TODO("VERSION.SDK.INT < LOLIPOP")
        }

        startActivityForResult(intent, READ_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // get bitmap image file as a result
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.also { uri ->
                Log.i(TAG, "Uri: $uri")
                bitmap = getBitmapFromUri(uri)
            }
        }
    }

    // get a bitmap file
    fun getBitmapFromUri(uri: Uri): Bitmap {
        val parcelFileDescriptor: ParcelFileDescriptor? = myContext.contentResolver.openFileDescriptor(uri, "r")
        val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
        val image: Bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor.close()
        return image
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewOfLayout = inflater.inflate(R.layout.fragment_second_import, container, false)
        fragManager = myContext.supportFragmentManager

        // Buttons
        val yesButton = viewOfLayout.findViewById<Button>(R.id.yesButton)
        val noButton = viewOfLayout.findViewById<Button>(R.id.noButton)

        // import if bitmap isInitialzed, or search for a image again
        yesButton.setOnClickListener {
            if (this::bitmap.isInitialized) {
                val newItem = GalleryItem(2, null, bitmap, null, null)
                caller.items.add(newItem)

                fragManager.popBackStack()
            } else {
                val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        type = "image/*"
                    }
                } else {
                    TODO("VERSION.SDK.INT < LOLIPOP")
                }
                startActivityForResult(intent, READ_REQUEST_CODE)
            }
        }

        noButton.setOnClickListener {
            fragManager.popBackStack()
        }

        return viewOfLayout
    }

    override fun onResume() {
        super.onResume()
        // Show the image to be imported
        if (this::bitmap.isInitialized) {
            val imageView = viewOfLayout.findViewById<ImageView>(R.id.imageCheck)
            imageView.setImageBitmap(bitmap)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SecondFragmentImport.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SecondFragmentImport().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}