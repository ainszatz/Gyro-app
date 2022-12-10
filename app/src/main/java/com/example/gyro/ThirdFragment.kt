package com.example.gyro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gyro.MainActivity.Companion.modeSet
import com.example.gyro.databinding.FragmentThirdBinding


class ThirdFragment : Fragment() {

    private var _binding: FragmentThirdBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinner = view.findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            var adapter = ArrayAdapter(requireActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, resources.getStringArray(R.array.Modes))
            spinner.adapter = adapter
        }
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View, p2: Int, p3: Long) {
                var modeSelected = spinner.selectedItem.toString()
                if (modeSelected=="Incline")
                {
                    view.findViewById<ImageView>(R.id.modeIcon).setImageResource(R.drawable.mode1)
                }
                else if (modeSelected=="Rotation")
                {
                    view.findViewById<ImageView>(R.id.modeIcon).setImageResource(R.drawable.mode2)
                }
                else if (modeSelected=="Orientation")
                {
                    view.findViewById<ImageView>(R.id.modeIcon).setImageResource(R.drawable.mode0)
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        binding.saveButton.setOnClickListener {
            findNavController().navigate(R.id.action_ThirdFragment_to_FirstFragment)
            modeSet = spinner.selectedItemPosition
        }
    }
}