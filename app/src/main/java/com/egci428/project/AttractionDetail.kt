package com.egci428.project

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import java.io.IOException
import java.io.InputStreamReader

class AttractionDetail : AppCompatActivity(), SensorEventListener {
    private var sensorManager: SensorManager? = null
    private val fileName = "attractions.txt"
    private var name = ""
    private var description = ""
    private var location = ""
    private var hours = ""
    private var rating = ""
    private var imageAddr = ""
    private var lat = ""
    private var long = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attraction_detail)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        name = intent.getStringExtra("name").toString()
        description = intent.getStringExtra("description").toString()
        location = intent.getStringExtra("location").toString()
        hours = intent.getStringExtra("hours").toString()
        rating = intent.extras!!.getFloat("rating").toString()
        imageAddr = intent.getStringExtra("imageAddr").toString()
        lat = intent.getStringExtra("lat").toString()
        long = intent.getStringExtra("long").toString()
        val detail = findViewById<TextView>(R.id.detail)
        val detName = findViewById<TextView>(R.id.detName)
        val detLocation = findViewById<TextView>(R.id.detLocation)
        val detDes = findViewById<TextView>(R.id.detDes)
        val detHours = findViewById<TextView>(R.id.detHours)
        val detRating = findViewById<RatingBar>(R.id.detRating)
        val detImage = findViewById<ImageView>(R.id.detImage)
        val saveAttraction = findViewById<Button>(R.id.saveAttraction)
        val backBtn = findViewById<Button>(R.id.backDetail)
        detail.text = name
        detName.text = name
        detLocation.text = location
        detDes.text = description
        detHours.text = "($hours)"
        detRating.rating = rating.toFloat()
        detImage.setImageResource(resources.getIdentifier(imageAddr,"drawable",packageName))

        saveAttraction.setOnClickListener{
            val fileContents = "${name};${description};${location};${hours};${rating};${imageAddr};${lat.toString()};${long.toString()}"

            try {
                val fileInputStream = openFileInput(fileName)
                val existingData = InputStreamReader(fileInputStream).readText()
                fileInputStream.close()

                if (existingData.contains(fileContents)) {
                    Toast.makeText(baseContext, "Location already saved", Toast.LENGTH_SHORT).show()
                } else {
                    val fileOutputStream = openFileOutput(fileName, Context.MODE_APPEND or Context.MODE_PRIVATE)
                    fileOutputStream.write(fileContents.toByteArray())
                    fileOutputStream.write(System.getProperty("line.separator").toByteArray())
                    fileOutputStream.close()
                    Toast.makeText(baseContext, "Added to favorite", Toast.LENGTH_SHORT).show()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(baseContext, "Can't add to favorite", Toast.LENGTH_SHORT).show()
            }
        }
        backBtn.setOnClickListener{
            finish()
        }

    }
    override fun onSensorChanged(event: SensorEvent) {
        println("==========================SENSOR CHANGE")
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    private fun getAccelerometer(event: SensorEvent) {
        println("==========================FN run")
        val values = event.values
        // Movement
        val x = values[0]
        val y = values[1]
        val z = values[2]

        val accel = (x * x + y * y + z * z) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH)
        if (accel >= 1)
        {
            println("==========================LIST ADDED")
            try {
                val fileContents = "${name};${description};${location};${hours};${rating};${imageAddr};${lat.toString()};${long.toString()}"
                val fileInputStream = openFileInput(fileName)
                val existingData = InputStreamReader(fileInputStream).readText()
                fileInputStream.close()

                if (existingData.contains(fileContents)) {
                    Toast.makeText(baseContext, "Location already saved", Toast.LENGTH_SHORT).show()
                } else {
                    val fileOutputStream = openFileOutput(fileName, Context.MODE_APPEND or Context.MODE_PRIVATE)
                    fileOutputStream.write(fileContents.toByteArray())
                    fileOutputStream.write(System.getProperty("line.separator").toByteArray())
                    fileOutputStream.close()
                    Toast.makeText(baseContext, "Added to favorite", Toast.LENGTH_SHORT).show()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(baseContext, "Can't add to favorite", Toast.LENGTH_SHORT).show()
            }
        }
    }
}