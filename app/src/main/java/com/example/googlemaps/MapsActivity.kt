package com.example.googlemaps

import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.GroundOverlayOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

class MapsActivity : AppCompatActivity() {

    lateinit var gMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_maps)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.maps)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val mapFragment: SupportMapFragment =
            supportFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment

        mapFragment.getMapAsync(object : OnMapReadyCallback {
            override fun onMapReady(googleMap: GoogleMap) {
                // Use 'googleMap' object to interact with the map

                gMap = googleMap
                val valsadLatLng = LatLng(20.6039609, 72.9337587)
                val markerOptions = MarkerOptions().position(valsadLatLng).title("Valsad, Gujarat")
                gMap.addMarker(markerOptions)
                gMap.moveCamera(CameraUpdateFactory.newLatLng(valsadLatLng))
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(valsadLatLng, 16f)) //16-20f

                //CIRCLE
                gMap.addCircle(
                    CircleOptions()
                        .center(valsadLatLng)
                        .radius(1000.00)
                        .fillColor(Color.TRANSPARENT)
                        .strokeColor(Color.GRAY)
                )

//                gMap.addGroundOverlay(GroundOverlayOptions()
//                    .position(valsadLatLng, 1000f, 1000f)
//                    .image(BitmapDescriptorFactory.fromResource(R.drawable.an)))

                // Set the map type to satellite view
                gMap.mapType = GoogleMap.MAP_TYPE_HYBRID

                gMap.setOnMapClickListener(object : GoogleMap.OnMapClickListener {
                    override fun onMapClick(latLng: LatLng) {
                        gMap.addMarker(MarkerOptions().position(latLng).title("Clicked here"))

                        val geocoder = Geocoder(this@MapsActivity)
                        try {
                            val arrAdr: ArrayList<Address> = geocoder.getFromLocation(
                                latLng.latitude,
                                latLng.longitude,
                                1
                            ) as ArrayList<Address>
                            Log.d("address", arrAdr.get(0).getAddressLine(0))
                            Toast.makeText(
                                this@MapsActivity,
                                arrAdr.get(0).getAddressLine(0),
                                Toast.LENGTH_SHORT
                            ).show()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                })


            }

        })


    }
}