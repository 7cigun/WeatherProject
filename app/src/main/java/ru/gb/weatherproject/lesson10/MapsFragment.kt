package ru.gb.weatherproject.lesson10

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.snackbar.Snackbar
import ru.gb.weatherproject.R
import ru.gb.weatherproject.databinding.FragmentMapsMainBinding
import java.util.*
import kotlin.collections.ArrayList

class MapsFragment : Fragment() {

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        val moscow = LatLng(55.0, 37.0)
        map.addMarker(MarkerOptions().position(moscow).title("Marker in Moscow"))
        map.moveCamera(CameraUpdateFactory.newLatLng(moscow))
        map.setOnMapLongClickListener {
            addMarkerToArray(it)
            drawLine()
        }
        map.uiSettings.isZoomControlsEnabled = true
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
        }

    }

    private lateinit var map: GoogleMap
    private val markers: ArrayList<Marker> = arrayListOf()

    private fun addMarkerToArray(location: LatLng) {
        val marker = setMarker(location, markers.size.toString(), R.drawable.ic_map_pin)
        markers.add(marker)
    }

    private fun drawLine() {
        var previousBefore: Marker? = null
        markers.forEach { current ->
            previousBefore?.let { previous ->
                map.addPolyline(
                    PolylineOptions().add(previous.position, current.position)
                        .color(Color.RED)
                        .width(5f)
                )
            }
            previousBefore = current
        }
    }

    private fun setMarker(
        location: LatLng,
        searchText: String,
        resourceId: Int
    ): Marker {
        return map.addMarker(
            MarkerOptions()
                .position(location)
                .title(searchText)
                .icon(BitmapDescriptorFactory.fromResource(resourceId))
        )!!
    }

    private var _binding: FragmentMapsMainBinding? = null
    private val binding: FragmentMapsMainBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        initView()
    }

    private fun initView() {
        binding.buttonSearch.setOnClickListener() {
            val searchText = binding.searchAddress.text.toString()
            val geocoder = Geocoder(requireContext(), Locale.getDefault())
            val results = geocoder.getFromLocationName(searchText, 1)
            if (results.size > 0) {
                val location = LatLng(
                    results[0].latitude,
                    results[0].longitude
                )
                map.addMarker(
                    MarkerOptions()
                        .position(location)
                        .title(searchText)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker))
                )
                map.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        location, 10f
                    )
                )
            } else {
                Snackbar.make(
                    binding.root,
                    "Не удалось найти ${searchText}",
                    Snackbar.LENGTH_LONG
                )
                    .show()
            }
        }
    }
}