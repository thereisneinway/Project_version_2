package com.egci428.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.egci428.project.Data.Attractions
import android.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.egci428.project.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.LatLngBounds
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var attractionsList: List<Attractions>
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fileName = "attractions.txt"
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        attractionsList = loadAttractionsFromFile(fileName)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val builder = LatLngBounds.Builder()

        for (attraction in attractionsList) {
            val latLng = LatLng(attraction.lat, attraction.long)
            val marker = mMap.addMarker(MarkerOptions().position(latLng).title(attraction.name))

            if (marker != null) {
                builder.include(marker.position)
            }

            val bounds = builder.build()
            val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 0)
            mMap.animateCamera(cameraUpdate)

        }


    }

    private fun loadAttractionsFromFile(fileName: String): List<Attractions> {
        val attractionsList = mutableListOf<Attractions>()

        try {
            val fIn = openFileInput(fileName)
            val mFile = InputStreamReader(fIn)
            val br = BufferedReader(mFile)
            var line: String? = br.readLine()

            while (line != null) {
                val attributes = line.split(",")

                if (attributes.size == 8) {
                    val name = attributes[0]
                    val description = attributes[1]
                    val location = attributes[2]
                    val hours = attributes[3]
                    val rating = attributes[4].toDouble()
                    val imageAddr = attributes[5]
                    val lat = attributes[6].toDouble()
                    val long = attributes[7].toDouble()

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
                    attractionsList.add(attraction)
                }
                line = br.readLine()
            }
            br.close()
            mFile.close()
            fIn.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return attractionsList
    }
}