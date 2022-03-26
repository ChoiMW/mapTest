package com.example.maptest

import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.maptest.databinding.ActivityMapsBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    var selectedMarker: Marker ?=null
   //var food_marker: TextView ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)




    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //Intent (출발액티비티 ,도착액티비티)
        val detailRestaurantActivityIntent = Intent(this, DetailRestaurantActivity::class.java) // 인텐트를 생성
//        setCustomMarkerView()
        getSampleMarkerItems()



        mMap.setOnMarkerClickListener(object: GoogleMap.OnMarkerClickListener {
            override fun onMarkerClick(marker: Marker): Boolean {
                val center: CameraUpdate = CameraUpdateFactory.newLatLng(marker.getPosition())
                mMap.animateCamera(center,300,null)
                changeSelectedMarker(marker)

                startActivity(detailRestaurantActivityIntent)
                return true
            }
        })

        mMap.setOnMapClickListener(object:GoogleMap.OnMapClickListener {
             override fun onMapClick(position : LatLng)  {
                if(selectedMarker != null){
                    var bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_food_black)
                    selectedMarker?.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap))
                }
                val center: CameraUpdate = CameraUpdateFactory.newLatLng(position)
                mMap.animateCamera(center,300,null)

            }

        })

        // Add a marker and move the camera
        val yeouido = LatLng(37.521814, 126.923596)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yeouido,13f))


    }

    private fun changeSelectedMarker(marker: Marker) {

        val resources: Resources = this.resources
        var bitmap:Bitmap
        var og_bitmap :Bitmap

        if(selectedMarker != null) {

            og_bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_food_black)
            selectedMarker?.setIcon(BitmapDescriptorFactory.fromBitmap(og_bitmap))

        }
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_food)
        marker.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap))
        selectedMarker=marker


    }



    class MarkerItem(lat: Double, lng: Double, restName: String) {
        var latitude = lat
        var longitude = lng
        var restaurantName = restName

        fun getLat() = latitude

        fun setLat( lat: Double) {
            this.latitude = lat
        }
        fun getLon() = longitude
        fun setLon(lng: Double) {
            this.longitude = lng
        }
        fun getRestName() = restaurantName

        fun setRestName(restName:String) {
            this.restaurantName = restName
        }
    }

    fun getSampleMarkerItems()
    {
        var sampleList = ArrayList<MarkerItem>()

        sampleList.add(  MarkerItem (37.521814, 126.983596, "AAA" ))
        sampleList.add(  MarkerItem (37.511804, 126.973596, "BBB"  ))
        sampleList.add(  MarkerItem (37.591854, 126.953426, "CCC" ))
        sampleList.add(  MarkerItem (37.531864, 126.963516, "DDD"  ))
        sampleList.add(  MarkerItem (37.501754, 126.943434, "EEE" ))
        sampleList.add(  MarkerItem (37.551764, 126.933543, "FFF"  ))
        sampleList.add(  MarkerItem (37.561654, 126.922451, "GGG" ))
        sampleList.add(  MarkerItem (37.551664, 126.923162, "HHH"  ))
        sampleList.add(  MarkerItem (37.521814, 126.923596, "III"  ))

        for ( markerItem : MarkerItem in sampleList) {
            addMarker(markerItem, false)
        }
    }
    private fun addMarker (marker : Marker, isSelectedMarker: Boolean) :Marker
    {

        var lat : Double = marker.getPosition().latitude;
        var lon : Double = marker.getPosition().longitude;
        var restName : String = marker.getTitle();
        var temp : MarkerItem =  MarkerItem(  lat,    lon,    restName    )
        return addMarker(temp, isSelectedMarker)

    }
    private fun addMarker (markerItem:MarkerItem, isSelectedMarker: Boolean) :Marker
    {
        var position:LatLng = LatLng(
            markerItem.getLat(),
            markerItem.getLon()
        )
        var restName :String = markerItem.getRestName()
        val resources: Resources = this.resources
        val bitmap:Bitmap

        if (isSelectedMarker) {
             bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_food)
        }
        else {
             bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_food_black)
        }
        var markerOptions: MarkerOptions = MarkerOptions()
        markerOptions.title(restName)
        markerOptions.position(position)
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap))

        return mMap.addMarker(markerOptions)
    }



}