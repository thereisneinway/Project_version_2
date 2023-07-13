package com.egci428.project

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import java.io.IOException
import java.io.InputStreamReader

class AttractionDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attraction_detail)
        //open files
        val fileName = "attractions.txt"
        val name = intent.getStringExtra("name")
        val description = intent.getStringExtra("description")
        val location = intent.getStringExtra("location")
        val hours = intent.getStringExtra("hours")
        val rating = intent.extras!!.getFloat("rating")
        val imageAddr = intent.getStringExtra("imageAddr")
        val lat = intent.getStringExtra("lat")
        val long = intent.getStringExtra("long")
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
        detRating.rating = rating
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
}