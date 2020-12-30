package com.hurrypizza.test

import android.Manifest.permission
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.pm.PackageManager as PackageManager1

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootview: View? = inflater.inflate(R.layout.fragment_first, container, false)

        val mytext = rootview?.findViewById<TextView>(R.id.mytext)
        val rv_contact = rootview?.findViewById<RecyclerView>(R.id.rv_contact)

        var ContactList = arrayListOf<ContactItem>()

        if (checkAndRequestPermission() == true) {
            var resolver: ContentResolver = requireActivity().contentResolver
            val c = resolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    null,
                    null,
                    null
            )

            if (c != null && c.count > 0) {
                c.moveToFirst()
                do {
                    var name: String = c.getString(
                            c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                    )
                    var phoneNumber = c.getString(
                            c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    )
                    ContactList.add(ContactItem(name, phoneNumber))
                } while (c.moveToNext())
            }

            val adapter = ContactAdapter(requireContext(), ContactList)
            rv_contact?.adapter = adapter

            val lm = LinearLayoutManager(requireContext())
            rv_contact?.layoutManager = lm
            rv_contact?.setHasFixedSize(true)

        } else {
            mytext?.text = "연락처를 불러올 수 없습니다. 권한을 설정해주세요."
        }


        return rootview
    }

    fun checkAndRequestPermission(): Boolean {
        if (checkSelfPermission(requireActivity(), android.Manifest.permission.READ_CONTACTS)
         == PERMISSION_GRANTED) {
            return true
        } else {
            requestPermissions(arrayOf(android.Manifest.permission.READ_CONTACTS), 101)
            return false
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FirstFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FirstFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}