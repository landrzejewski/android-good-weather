package pl.training.goodweather.tracking.adapter.view

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.PermissionChecker.PERMISSION_DENIED
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import pl.training.goodweather.GoodWeatherApplication.Companion.componentsGraph
import pl.training.goodweather.R
import pl.training.goodweather.commons.logging.Logger
import pl.training.goodweather.commons.view.centerCamera
import pl.training.goodweather.commons.view.drawRoute
import pl.training.goodweather.configuration.Values.CAMERA_ZOOM
import pl.training.goodweather.databinding.FragmentTrackingBinding
import pl.training.goodweather.tracking.model.Position
import javax.inject.Inject

class TrackingFragment: Fragment(), GoogleApiClient.ConnectionCallbacks {

    private val locationPermissionRequestCode = 999
    private val viewModel: TrackingViewModel by activityViewModels()
    private lateinit var map: GoogleMap
    private lateinit var binding: FragmentTrackingBinding
    private lateinit var googleApiClient: GoogleApiClient
    @Inject
    lateinit var logger: Logger
    @Inject
    lateinit var locationRequest: LocationRequest
    private var lastLocation: Location? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        componentsGraph.inject(this)
        createGoogleApiClient()
        binding = FragmentTrackingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.trackingMap) as SupportMapFragment
        mapFragment.getMapAsync(callback)
    }

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        if (checkSelfPermission(requireContext(), ACCESS_FINE_LOCATION) == PERMISSION_DENIED) {
            requestPermissions(arrayOf(ACCESS_FINE_LOCATION), locationPermissionRequestCode)
        } else {
            configureMap()
            googleApiClient.connect()
        }
    }

    @SuppressWarnings("MissingPermission")
    private fun configureMap() {
        map.isMyLocationEnabled = true
        map.uiSettings.isCompassEnabled = false
        map.uiSettings.isMyLocationButtonEnabled = false
    }

    private fun createGoogleApiClient() {
        googleApiClient = GoogleApiClient.Builder(requireContext())
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener { logger.log("Connection to Google Play Services failed") }
            .addApi(LocationServices.API)
            .build()
    }

    override fun onConnected(p0: Bundle?) {
        startLocationUpdates()
    }

    override fun onConnectionSuspended(p0: Int) {
        logger.log("Connection to Google Play Services suspended")
    }

    private fun startLocationUpdates() {
        if (checkSelfPermission(requireContext(), ACCESS_FINE_LOCATION) == PERMISSION_DENIED) {
            requestPermissions(arrayOf(ACCESS_FINE_LOCATION), locationPermissionRequestCode)
        } else {
            requestLocationUpdates()
            viewModel.start()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (permissions[0] == ACCESS_FINE_LOCATION && grantResults[0] == PERMISSION_GRANTED) {
            if (requestCode == locationPermissionRequestCode) {
                startLocationUpdates()
            }
        }
    }

    @SuppressWarnings("MissingPermission")
    private fun requestLocationUpdates() {
        LocationServices.getFusedLocationProviderClient(requireContext())
            .requestLocationUpdates(locationRequest, locationUpdateCallback , Looper.getMainLooper())
    }

    private val locationUpdateCallback = object : LocationCallback() {

        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)
            locationResult?.let {onLocationUpdate(locationResult.lastLocation)}
        }

    }

    private fun onLocationUpdate(location: Location) {
        map.centerCamera(location)
        val distance = if (lastLocation == null) 0F else location.distanceTo(lastLocation)
        val position = Position(location.longitude, location.latitude)
        viewModel.onLocationChange(position, location.speed, distance)
        map.drawRoute(lastLocation, location)
        updateStats()
        lastLocation = location
    }

    private fun updateStats() {
        binding.durationTextView.text = viewModel.duration
        binding.speedTextView.text = viewModel.speed
        binding.paceTextView.text = viewModel.pace
    }

}