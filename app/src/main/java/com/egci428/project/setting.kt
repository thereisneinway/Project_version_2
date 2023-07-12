package com.egci428.project

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class setting : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting)
        val aboutUsBtn = findViewById<Button>(R.id.aboutUsBtn)
        val creditBtn = findViewById<Button>(R.id.creditBtn)
        val profileBtn2 = findViewById<Button>(R.id.profileBtn2)
        val backBtn = findViewById<ImageButton>(R.id.sbackBtn)
        creditBtn.setOnClickListener(){
           val intent = Intent(this, CreditPage::class.java)
            startActivity(intent)
        }
        backBtn.setOnClickListener(){
            finish()
        }
        aboutUsBtn.setOnClickListener(){
            val intent = Intent(this, AboutUs::class.java)
            startActivity(intent)
        }
        profileBtn2.setOnClickListener(){
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }
    }
}