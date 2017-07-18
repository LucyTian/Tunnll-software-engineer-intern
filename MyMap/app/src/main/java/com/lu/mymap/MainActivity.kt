package com.lu.mymap

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment;

import android.widget.ImageButton

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.CameraUpdateFactory



class MainActivity : AppCompatActivity(),OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnExit: ImageButton = findViewById(R.id.btn1)

        btnExit.setOnClickListener {
            finish()
            System.exit(0)
        }

        val mapFragment:SupportMapFragment = supportFragmentManager.
                findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val sydney: LatLng = LatLng(-33.852,151.211)
        googleMap.addMarker(MarkerOptions().position(sydney).title("This is Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}