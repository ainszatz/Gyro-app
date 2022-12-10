package com.example.gyro

import android.app.Activity
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gyro.databinding.FragmentSecondBinding
import com.example.gyro.MainActivity.Companion.modeSet
import kotlin.math.abs

class SecondFragment : Fragment(), SensorEventListener {

    private var _binding: FragmentSecondBinding? = null
    private var unpaused = true
    private var mSensorManager: SensorManager? = null
    private var mode = modeSet
    private var gyroVal: Float = 0f
    private var accele = FloatArray(3)
    private var magnet = FloatArray(3)
    private var rotm = FloatArray(9)
    private var orim = FloatArray(3)
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (mode==0)
        {view.findViewById<ImageView>(R.id.modeIcon).setImageResource(R.drawable.mode0)}
        else if (mode==1)
        {view.findViewById<ImageView>(R.id.modeIcon).setImageResource(R.drawable.mode1)}
        else
        {view.findViewById<ImageView>(R.id.modeIcon).setImageResource(R.drawable.mode2)}
        super.onViewCreated(view, savedInstanceState)
        mSensorManager = this.requireActivity().getSystemService(Activity.SENSOR_SERVICE) as SensorManager
        this.registerSensorListener()
        binding.buttonSecond.setOnClickListener {
            pauseUnpause(view)
        }

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        binding.modeButton.setOnClickListener {
            if (mode==0)
            {
                mode=1
                view.findViewById<ImageView>(R.id.modeIcon).setImageResource(R.drawable.mode1)
            }
            else if (mode==1)
            {
                mode=2
                view.findViewById<ImageView>(R.id.modeIcon).setImageResource(R.drawable.mode2)
            }
            else
            {
                mode=0
                view.findViewById<ImageView>(R.id.modeIcon).setImageResource(R.drawable.mode0)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        _binding = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSensorChanged(ev: SensorEvent?) {
        if (ev!!.sensor.type == Sensor.TYPE_ACCELEROMETER)
            {accele = ev.values}
        if (ev.sensor.type == Sensor.TYPE_MAGNETIC_FIELD)
            {magnet = ev.values}
        if (unpaused)
        {
            SensorManager.getRotationMatrix(rotm, null, accele, magnet)
            SensorManager.getOrientation(rotm, orim)
            gyroVal = orim[mode]
            updateText(gyroVal)
        }
    }

    fun updateText(v: Float){
        val view = super.getView()
        if (view != null) {
            view.findViewById<TextView>(R.id.sensor_value).text = (abs(Math.toDegrees(v.toDouble()))).format()
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    fun pauseUnpause(view: View){
        unpaused = !unpaused
        if (view.findViewById<Button>(R.id.button_second).text == "Pause")
        {
            view.findViewById<Button>(R.id.button_second).text = "Resume"
        }
        else
        {
            view.findViewById<Button>(R.id.button_second).text = "Pause"
        }
    }

    private fun registerSensorListener() {
        mSensorManager!!.registerListener(
            this,
            mSensorManager!!.getSensorList(Sensor.TYPE_ACCELEROMETER)[0],
            SensorManager.SENSOR_DELAY_FASTEST
        )
        mSensorManager!!.registerListener(
            this,
            mSensorManager!!.getSensorList(Sensor.TYPE_MAGNETIC_FIELD)[0],
            SensorManager.SENSOR_DELAY_FASTEST
        )
    }
}

private fun Double.format() = "%.1f".format(this)