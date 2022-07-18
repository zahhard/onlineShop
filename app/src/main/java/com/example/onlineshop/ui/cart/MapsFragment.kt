package com.example.onlineshop.ui.cart

import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import android.location.LocationListener
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.onlineshop.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */


        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    private lateinit var mGoogleMap: GoogleMap
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        sharedPreferences = requireActivity().getSharedPreferences("login", Context.MODE_PRIVATE)

        var lat = sharedPreferences.getString("Latitude", "")
        var long = sharedPreferences.getString("Longitude", "")

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        val location = LatLng(lat!!.toDouble(), long!!.toDouble())


//
//        mGoogleMap = GoogleMap()
//        mGoogleMap!!.clear()
//        mGoogleMap!!.addMarker(MarkerOptions().position(location).title("Current Location"))
//        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(location))


//            override fun onMapReady(googleMap: GoogleMap) {
//
//                mGoogleMap = googleMap;
//
//                if (mGoogleMap != null) {
//                    mGoogleMap!!.addMarker(
//                        MarkerOptions().position(LatLng(latitude, longitude))
//                            .title("Current Location")
//                    )
//                }
//
//            }



    }


    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            Log.d ("fff", "" + location.longitude + ":" + location.latitude)
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }
}