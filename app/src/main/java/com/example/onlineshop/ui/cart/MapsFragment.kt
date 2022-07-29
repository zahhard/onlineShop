package com.example.onlineshop.ui.cart

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.onlineshop.R
import com.example.onlineshop.databinding.FragmentMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsFragment : Fragment() {

    lateinit var binding: FragmentMapsBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var map: GoogleMap
    lateinit var lat:String
    lateinit var long:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLocationPermission()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { readyMap ->
            Log.d("location", "map ready")
            map = readyMap
        }
        binding.btnConfirm.setOnClickListener {
            val shared = activity?.getSharedPreferences("lat_long",Context.MODE_PRIVATE)
            val editor = shared?.edit()
            Toast.makeText(requireContext(), "latitude: $lat \n longitude: $long" ,  Toast.LENGTH_SHORT).show()
            editor?.putString("latitude", lat)
            editor?.putString("longitude", long)
            editor?.apply()
//            findNavController().navigate(R.id.action_mapsFragment_to_newAddressFragment)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLocationPermission() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    showLocation()
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    showLocation()
                }
                else -> {
                    Toast.makeText(requireContext(), "goodbye", Toast.LENGTH_SHORT).show()
                }
            }
        }

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }
    @SuppressLint("MissingPermission")
    private fun showLocation() {
        if(!isLocationEnabled()){
            Toast.makeText(requireContext(), "لطفا لوکیشن گوشی را روشن کنید", Toast.LENGTH_LONG).show()
        }
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
            location?.let{
                binding.btnConfirm.isEnabled = true
                it.time
                lat = it.latitude.toString()
                long = it.longitude.toString()
                showLocationOnMap(LatLng(it.latitude , it.longitude))
            }
        }
        fusedLocationClient.getCurrentLocation(	PRIORITY_HIGH_ACCURACY , null).addOnSuccessListener{
                location : Location? ->
            location?.let{
                lat = it.latitude.toString()
                long = it.longitude.toString()
            }
        }
    }

    private fun showLocationOnMap(latLng: LatLng) {
        map.setMinZoomPreference(6.0f)
        map.setMaxZoomPreference(14.0f)
        map.addMarker(
            MarkerOptions()
                .position(latLng)
                .title("Marker in location")
                .zIndex(2.0f))
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng))

    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return LocationManagerCompat.isLocationEnabled(locationManager)
    }
}