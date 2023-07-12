package com.egci428.project

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class CreditPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.credit_page)

        val backBtn = findViewById<ImageButton>(R.id.sbackBtn)

        backBtn.setOnClickListener(){
            finish()
        }

    }
}