package com.egci428.project



import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri

class Profile : AppCompatActivity() {
    private lateinit var profileImage: ImageView
    private lateinit var nameEditText: EditText
    private lateinit var dobEditText: EditText
    private lateinit var detailEditText: EditText
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_page)

        sharedPreferences = getSharedPreferences("ProfileData", Context.MODE_PRIVATE)

        profileImage = findViewById(R.id.profile_image)
        nameEditText = findViewById(R.id.name_edit_text)
        dobEditText = findViewById(R.id.dob_edit_text)
        detailEditText = findViewById(R.id.detail_edit_text)

        val backBtn3 = findViewById<ImageButton>(R.id.profileBackBtn)
        backBtn3.setOnClickListener {
            intent = Intent(this, setting::class.java)
            startActivity(intent)
        }

        val change_picture_button = findViewById<Button>(R.id.change_picture_button)

        val savedName = sharedPreferences.getString("name", "")
        val savedDob = sharedPreferences.getString("dob", "")
        val savedDetail = sharedPreferences.getString("detail", "")
        val savedImageUri = sharedPreferences.getString("imageUri", "")

        nameEditText.setText(savedName)
        dobEditText.setText(savedDob)
        detailEditText.setText(savedDetail)
//        if (!savedImageUri.isNullOrEmpty()) {
//            profileImage.setImageURI(savedImageUri.toUri())
//        }

        val loadsImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            profileImage.setImageURI(uri)
            val imageUriString = uri.toString()
            saveData("imageUri", imageUriString)
        }

        change_picture_button.setOnClickListener {
            loadsImage.launch("image/*")
        }
    }

    private fun saveData(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun saveProfile(view: View) {
        val name = nameEditText.text.toString()
        val dob = dobEditText.text.toString()
        val detail = detailEditText.text.toString()

        saveData("name", name)
        saveData("dob", dob)
        saveData("detail", detail)
    }
}
