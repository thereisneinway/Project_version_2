package com.egci428.project

import android.content.Intent
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.egci428.project.Data.AttractionAdapter
import com.egci428.project.Data.Attractions
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class FavoriteList : AppCompatActivity(),AttractionAdapter.ClickListener {

    lateinit var recyclerView: RecyclerView
    var favoriteList : ArrayList<Attractions> = ArrayList()
    var adapter = AttractionAdapter(favoriteList, this,this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_list)

        val backBtn = findViewById<Button>(R.id.backList2)
        val favoritetitle = findViewById<TextView>(R.id.favoriteTitle)
        val mapBtn = findViewById<Button>(R.id.openmaps)
        recyclerView = findViewById(R.id.favoriteLists2)
        recyclerView.layoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        loadFavoriteAttractions()
        backBtn.setOnClickListener {
            finish()
        }
        mapBtn.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
    }
    private fun loadFavoriteAttractions() {


        try {
            val fileInputStream = openFileInput("attractions.txt")
            val reader = BufferedReader(InputStreamReader(fileInputStream))
            var line: String?

            while (reader.readLine().also { line = it } != null) {
                val attributes = line!!.split(";")


                    val name = attributes[0]
                    val description = attributes[1]
                    val location = attributes[2]
                    val hours = attributes[3]
                    val rating = attributes[4].toDouble()
                    val imageAddr = attributes[5]
                    var lat = 0.0
                    var long = 0.0
                    if(attributes[6] != "null" ){
                        lat = attributes[6].toDouble()
                    }
                    if(attributes[6] != "null" ){
                        long = attributes[7].toDouble()
                    }


                    val attraction = Attractions(
                        name = name,
                        description = description,
                        location = location,
                        hours = hours,
                        rating = rating,
                        imageAddr = imageAddr,
                        lat = lat,
                        long = long
                    )
                    favoriteList.add(attraction)

            }
            adapter.notifyDataSetChanged()
            Toast.makeText(this@FavoriteList,"Data fetched", Toast.LENGTH_SHORT).show()

            reader.close()
            fileInputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this@FavoriteList,"Data fail", Toast.LENGTH_SHORT).show()
        }
    }

    override fun clickedItem(attractionsModel: Attractions) {
        val intent = Intent(this, AttractionDetail::class.java)
        intent.putExtra("name", attractionsModel.name)
        intent.putExtra("description", attractionsModel.description)
        intent.putExtra("location", attractionsModel.location)
        intent.putExtra("hours", attractionsModel.hours)
        intent.putExtra("rating", attractionsModel.rating.toFloat())
        intent.putExtra("imageAddr", attractionsModel.imageAddr)
        intent.putExtra("lat", attractionsModel.lat)//Double
        intent.putExtra("long", attractionsModel.long)//Double
        startActivity(intent)

    }
}