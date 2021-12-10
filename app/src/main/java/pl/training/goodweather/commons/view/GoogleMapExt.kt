package pl.training.goodweather.commons.view

import android.location.Location
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import pl.training.goodweather.R
import pl.training.goodweather.configuration.Values.CAMERA_ZOOM

fun GoogleMap.drawRoute(lastLocation: Location?, location: Location) {
    lastLocation?.let {
        val options = PolylineOptions()
        options.color(R.color.black)
        options.width(10F)
        options.add(LatLng(it.latitude, it.longitude))
        options.add(LatLng(location.latitude, location.longitude))
        addPolyline(options)
    }
}

fun GoogleMap.centerCamera(location: Location) {
    val position = LatLng(location.latitude, location.longitude)
    val cameraUpdate = CameraUpdateFactory.newLatLngZoom(position, CAMERA_ZOOM)
    animateCamera(cameraUpdate)
}