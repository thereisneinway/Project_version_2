package com.egci428.project

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.egci428.project.Data.Attractions
import com.egci428.project.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private var builder = LatLngBounds.Builder()
    private var attractionsList: ArrayList<Attractions> = ArrayList<Attractions>()
    private lateinit var binding: ActivityMapsBinding
    private var locationManager: LocationManager? = null
    private var locationListener: LocationListener? = null
    private lateinit var currentLatLng : LatLng


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener = object: LocationListener{
            override fun onLocationChanged(location: Location) {
                mMap.addMarker(MarkerOptions()
                    .position(LatLng(location.latitude,location.longitude))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))
            }
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
        }
        request_location()
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap){
        mMap = googleMap

        loadAttractionsFromFile("attractions.txt")
        for (attraction in attractionsList) {
            val latLng = LatLng(attraction.lat, attraction.long)
            val marker = mMap.addMarker(MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .title(attraction.name))
            if (marker != null) {
                builder.include(marker.position).build()
            }

            val bounds = builder.build()
            val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 0)

            mMap.animateCamera(cameraUpdate)
        }



    }

    private fun loadAttractionsFromFile(fileName: String){
        try {
            val fileInputStream = openFileInput("attractions.txt")
            val reader = BufferedReader(InputStreamReader(fileInputStream))
            var line: String?

            while (reader.readLine().also { line = it } != null) {
                val attributes = line!!.split(";")

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
            }
            reader.close()
            fileInputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    private fun request_location() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET), 10)
                requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), 10)
            }
            return
        }
        locationManager!!.requestLocationUpdates("gps",5000, 0F, locationListener!!)

    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            10 -> request_location()
            else -> {
                Log.d("PermissionResult", "Fail to receive request code")
            }
        }
    }
}