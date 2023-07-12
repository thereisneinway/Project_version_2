package com.egci428.project

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class AboutUs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.aboutus_page)

        val backBtn = findViewById<ImageButton>(R.id.aboutbackBtn)

        backBtn.setOnClickListener(){
            finish()
        }

    }
}