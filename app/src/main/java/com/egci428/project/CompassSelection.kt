package com.egci428.project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class CompassSelection : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.compass_selection)
        val backBtn = findViewById<Button>(R.id.backcompass)
        val northBtn = findViewById<Button>(R.id.northBtn)
        val southBtn = findViewById<Button>(R.id.southBtn)
        val westBtn = findViewById<Button>(R.id.westBtn)
        val eastBtn = findViewById<Button>(R.id.eastBtn)
        val centralBtn = findViewById<Button>(R.id.centralBtn)
        val northeastBtn = findViewById<Button>(R.id.northeastBtn)

        backBtn.setOnClickListener{
            finish()
        }
        northBtn.setOnClickListener {
            val intent = Intent(this, AttractionList::class.java)
            intent.putExtra("region", "north")
            startActivity(intent)
        }

        southBtn.setOnClickListener {
            val intent = Intent(this, AttractionList::class.java)
            intent.putExtra("region", "south")
            startActivity(intent)
        }

        westBtn.setOnClickListener {
            val intent = Intent(this, AttractionList::class.java)
            intent.putExtra("region", "west")
            startActivity(intent)
        }

        eastBtn.setOnClickListener {
            val intent = Intent(this, AttractionList::class.java)
            intent.putExtra("region", "east")
            startActivity(intent)
        }

        centralBtn.setOnClickListener {
            val intent = Intent(this, AttractionList::class.java)
            intent.putExtra("region", "central")
            startActivity(intent)
        }

        northeastBtn.setOnClickListener {
            val intent = Intent(this, AttractionList::class.java)
            intent.putExtra("region", "northeast")
            startActivity(intent)
        }
    }
}