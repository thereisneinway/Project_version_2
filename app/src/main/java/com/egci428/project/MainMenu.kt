package com.egci428.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView

class MainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val planButton = findViewById<Button>(R.id.planBtn)
        val listView = findViewById<ListView>(R.id.listView)
        val profileBtn = findViewById<ImageButton>(R.id.profileBtn)
        val settingBtn = findViewById<ImageButton>(R.id.settingBtn)

        planButton.setOnClickListener {
            val intent = Intent(this, CompassSelection::class.java)
            startActivity(intent)
        }

        settingBtn.setOnClickListener(){
            val intent = Intent(this, setting::class.java)
            startActivity(intent)
        }

        profileBtn.setOnClickListener(){
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }
    }


}