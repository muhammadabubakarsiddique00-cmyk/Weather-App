package com.example.weatherapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    private lateinit var btnLocation: Button
    private lateinit var locationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLocation = findViewById(R.id.btnLocation)

        locationClient =
            LocationServices.getFusedLocationProviderClient(this)

        btnLocation.setOnClickListener {

            if (
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED ||

                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {

                getLocation()

            } else {

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    100
                )
            }
        }
    }

    private fun getLocation() {


        if (
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        locationClient.getCurrentLocation(
            com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY,
            null
        ).addOnSuccessListener { location ->

            if (location != null) {

                val latitude = location.latitude
                val longitude = location.longitude

                val intent = Intent(this,WeatherActivity::class.java)
                intent.putExtra("longitude",longitude)
                intent.putExtra ("latitude",latitude)
                startActivity(intent)

                Toast.makeText(
                    this,
                    "Latitude: $latitude\nLongitude: $longitude",
                    Toast.LENGTH_LONG
                ).show()

            } else {

                Toast.makeText(
                    this,
                    "Location not found",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        super.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults
        )

        if (requestCode == 100) {

            if (
                grantResults.any {
                    it == PackageManager.PERMISSION_GRANTED
                }
            ) {

                getLocation()

            } else {

                Toast.makeText(
                    this,
                    "Permission Denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}