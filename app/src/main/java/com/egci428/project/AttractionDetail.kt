package com.egci428.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast

class AttractionDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attraction_detail)
        //open files

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
            //check if duplicate
            //save Lat long to file (save name lat long)
            Toast.makeText(this,"saved", Toast.LENGTH_SHORT).show()
            //if duplicate then toast error
        }
        backBtn.setOnClickListener{
            finish()
        }

    }
}