package com.example.gyro

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gyro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mSensorManager : SensorManager
    private var mAccelerometer : Sensor ?= null
    private var mMagnetometer : Sensor ?= null
    private lateinit var binding: ActivityMainBinding

    companion object{
        var modeSet : Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}