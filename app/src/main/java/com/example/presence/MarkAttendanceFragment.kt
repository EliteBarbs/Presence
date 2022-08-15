package com.example.presence

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup



class MarkAttendanceFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =inflater.inflate(R.layout.fragment_mark_attendance, container, false)
        /*val recognize:Button=view.findViewById(R.id.openCamera)
        recognize.setOnClickListener{
            val intent=Intent(activity,Face::class.java)
            activity?.startActivity(intent)
        }*/
        return view;
    }

}