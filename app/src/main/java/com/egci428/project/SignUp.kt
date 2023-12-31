package com.egci428.project

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.lang.Exception

class SignUp : AppCompatActivity() {

    private val file = "users.txt"
    lateinit var userSignText: EditText
    lateinit var passSignText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_activity)
        val addBtn = findViewById<Button>(R.id.addBtn)
        val backBtn = findViewById<Button>(R.id.backSignup)
        userSignText = findViewById(R.id.userSignText)
        passSignText = findViewById(R.id.passSignText)

        backBtn.setOnClickListener{
            finish()
        }
        addBtn.setOnClickListener {
            val usr = userSignText.text.toString()
            val pwd = passSignText.text.toString()
            if (usr.isEmpty()) {
                userSignText.error = "Please enter your username"
                if(pwd.isEmpty()){
                    passSignText.error = "Please enter your password"
                }
            }else{
                saveData()
                finish()
            }
        }
    }

    private fun saveData() {
        val usr = userSignText.text.toString()
        val pwd = passSignText.text.toString()
        Log.d("Debug", usr)
        val userData = User(usr, pwd)
        val line = userData.username+","+userData.password+"\n"
        try {
            val fOut = openFileOutput(file, Context.MODE_APPEND)
            fOut.write(line.toByteArray())
            fOut.close()
            Toast.makeText(applicationContext, "Saved Account", Toast.LENGTH_SHORT).show()
        } catch(e: Exception){
            e.printStackTrace()
        }
    }

}