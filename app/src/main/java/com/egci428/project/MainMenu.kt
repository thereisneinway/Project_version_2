package com.egci428.project

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar


class MainMenu : AppCompatActivity() {
    private val Channel_ID = "100"
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private val notificationManager: NotificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            refreshPermissionStatus()
            if(it){
                notifyMessage("EGCI428", "Hello World")

            }else{
                Snackbar.make(findViewById<View>(android.R.id.content).rootView, "Please grant Notification permission from setting", Snackbar.LENGTH_LONG).show()
            }
        }
        createNotificationChannel()

        val planButton = findViewById<Button>(R.id.planBtn)
        val profileBtn = findViewById<ImageButton>(R.id.profileBtn)
        val settingBtn = findViewById<ImageButton>(R.id.settingBtn)
        val gimmicBtn = findViewById<Button>(R.id.gimmicBtn)
        val logo = findViewById<ImageView>(R.id.logoImageView)
        logo.setImageResource(resources.getIdentifier("logo","mipmap",packageName))

        planButton.setOnClickListener {
            val intent = Intent(this, CompassSelection::class.java)
            startActivity(intent)
        }

        settingBtn.setOnClickListener(){
            val intent = Intent(this, setting::class.java)
            startActivity(intent)
        }

        profileBtn.setOnClickListener(){
            val intent = Intent(this, FavoriteList::class.java)
            startActivity(intent)
        }
        gimmicBtn.setOnLongClickListener {
            val fileOutputStream = openFileOutput("attractions.txt",  Context.MODE_PRIVATE)
            fileOutputStream.write("".toByteArray())
            fileOutputStream.close()
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){
                notifyMessage("TRAVEL PLANNER", "Goodbye")
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
            refreshPermissionStatus()
            System.exit(1)
            return@setOnLongClickListener true
        }
    }
    private fun notifyMessage(title: String, message: String){
        val intent = Intent(this, MainMenu::class.java)

        val pendingIntent = PendingIntent.getActivity(this, 1001, intent, PendingIntent.FLAG_IMMUTABLE)

        val optionAction = NotificationCompat.Action.Builder(R.drawable.ic_launcher_foreground, "Main Page", pendingIntent).build()


        val icon = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_foreground)
        val mBuilder = NotificationCompat.Builder(this, "100")
            .setLargeIcon(icon)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .addAction(optionAction)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager=NotificationManagerCompat.from(this)
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
            return
        }
        notificationManager.notify(0, mBuilder.build())
    }
    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "EGCI428"
            var description = "EGCI428 Notification Channel"
            var importance = NotificationManager.IMPORTANCE_DEFAULT
            var channel = NotificationChannel(Channel_ID, name, importance).apply {
                description=description
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    private fun refreshPermissionStatus(){
        notificationManager.areNotificationsEnabled()
    }
}