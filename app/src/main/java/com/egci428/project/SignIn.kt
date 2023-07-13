package com.egci428.project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.egci428.project.User
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception


class SignIn : AppCompatActivity() {
    private val file = "users.txt"
    var uname: String? = null
    var pname: String? = null
    var userprofile: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_page)

        val signInBtn = findViewById<Button>(R.id.signInBtn)
        val signUpBtn = findViewById<Button>(R.id.signUpBtn)
        val cancelBtn = findViewById<Button>(R.id.cancelBtn)
        val userText = findViewById<EditText>(R.id.userText)
        val passText = findViewById<EditText>(R.id.passText)

        signUpBtn.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
        signInBtn.setOnClickListener {
            uname = userText.text.toString()
            pname = passText.text.toString()
            if (!uname.isNullOrEmpty() && !pname.isNullOrEmpty()) {
                try {
                    val fIn = openFileInput(file)
                    val mfile = InputStreamReader(fIn)
                    val br = BufferedReader(mfile)
                    var line: String?
                    var matchFound = false

                    while (br.readLine().also { line = it } != null) {
                        val userItem = line?.split(",")
                        if (userItem?.get(0) == uname && userItem?.get(1) == pname) {
                            matchFound = true
                            break
                        }
                    }

                    if (matchFound) {
                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainMenu::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Incorrect username or password", Toast.LENGTH_SHORT).show()
                    }
                    br.close()
                } catch (e: Exception){
                    e.printStackTrace()
                }
            }else{ Toast.makeText(this, "Provide valid username or password", Toast.LENGTH_SHORT).show()
            }
            //for development
        }
        cancelBtn.setOnClickListener {
            userText.text = null
            passText.text = null
        }
    }

}