package com.delvin.uywalkyc

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.delvin.uywalkyc.RouteSchema.RouteResponse
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PaseadorMapaActivity : AppCompatActivity(), OnMapReadyCallback {
    private var start: String = ""
    private var end: String = ""
    private var latitudPaseador: Double = 0.0
    private var longitudPaseador: Double = 0.0
    private var mGoogleMap: GoogleMap? = null
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    var poly: Polyline? =null
    private var markerIcon: BitmapDescriptor? = null
    private var markerIconPaseador: BitmapDescriptor? = null

    private lateinit var coordenadasPaseador: LatLng
    private lateinit var coordenadasCliente: LatLng

    private var wayPoints: ArrayList<LatLng> = ArrayList()
    private val WAY_POINT_TAG = "way_point_tag"
/*    private lateinit var directionutil: DirectionUtil*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paseador_mapa)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        latitudPaseador = intent.getStringExtra("latitud")?.toDoubleOrNull() ?: 0.0
        longitudPaseador = intent.getStringExtra("longitud")?.toDoubleOrNull() ?: 0.0
        Log.d("LAT", latitudPaseador.toString())
        Log.d("LNGGG", longitudPaseador.toString())
        coordenadasPaseador = LatLng(latitudPaseador, longitudPaseador)

        // Log de las coordenadas del paseador
        Log.d("CUURPASE", coordenadasPaseador.toString())

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        markerIcon = getMarkerFromDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.basset)!!)
        markerIconPaseador = getMarkerFromDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.iconwalker)!!)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
        mapStyle()
        mGoogleMap!!.uiSettings.isZoomControlsEnabled = true
        setUpMap()

        // Agregar un nuevo marcador de paseador
        mGoogleMap?.addMarker(
            MarkerOptions()
                .position(coordenadasPaseador)
                .title("Paseador")
                .icon(markerIconPaseador)
        )
    }
/*

    fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.openrouteservice.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
*/

   /* private fun createRoute() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(PostApiService::class.java).getRoute(
                "5b3ce3597851110001cf6248ae0b07e60eb743bb8278d4ae518a4444",
                "${coordenadasPaseador.latitude},${coordenadasPaseador.longitude}",
                "${coordenadasCliente.latitude},${coordenadasCliente.longitude}"
            )
            if (call.isSuccessful) {
                drawRoute(call.body())
            } else {
                Log.d("aris", "KO")
            }
        }
    }*/

    private fun drawRoute(routeResponse: RouteResponse?) {
        val polylineOptions = PolylineOptions()
        routeResponse?.features?.first()?.geometry?.coordinates?.forEach{
            polylineOptions.add(LatLng(it[1],it[0]))
        }
        runOnUiThread{
             poly = mGoogleMap?.addPolyline(polylineOptions)!!
        }

    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null) {
                lastLocation = location
                coordenadasCliente = LatLng(lastLocation.latitude, lastLocation.longitude)
                Log.d("CUUR", coordenadasCliente.toString())
                val boundsBuilder = LatLngBounds.Builder()
                    .include(coordenadasPaseador)
                    .include(coordenadasCliente)
                val bounds = boundsBuilder.build()
                val padding = 500
                val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
                mGoogleMap?.animateCamera(cameraUpdate)

                // Agregar un marcador para el cliente
                markerOnMap(coordenadasCliente)
            }
        }
    }

    private fun markerOnMap(currentLatLong: LatLng) {
        val markerOptions = MarkerOptions().position(currentLatLong).icon(markerIcon)
        markerOptions.title("Tú")
        mGoogleMap?.addMarker(markerOptions)
        Log.d("MARCADOR", "Marcador del cliente agregado en: $currentLatLong")
    }

    private fun getMarkerFromDrawable(drawable: Drawable): BitmapDescriptor {
        val canvas = Canvas()
        val bitmap = Bitmap.createBitmap(
            150,
            150,
            Bitmap.Config.ARGB_8888
        )
        canvas.setBitmap(bitmap)
        drawable.setBounds(0, 0, 150, 150)
        drawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private fun mapStyle() {
        try {
            val success = mGoogleMap?.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(this, R.raw.style)
            )
            if (!success!!) {
                Log.d("MAPAS", "No se pudo encontrar el estilo")
            }
        } catch (e: Resources.NotFoundException) {
            Log.d("MAPAS", "Error: ${e.toString()}")
        }
    }

    /*private fun easyDrawRoute(){
        wayPoints.add(coordenadasPaseador)
        wayPoints.add(coordenadasCliente)
        directionutil=DirectionUtil.Builder()
            .setDirectionKey(resources.getString(R.string.google_map_api_key))
            .setOrigin(coordenadasPaseador)
            .setWayPoints(wayPoints)
            .setGoogleMap(mGoogleMap)
            .setPolyLinePrimaryColor(R.color.orangepalid)
            .setPolyLineWidth(10)
            .setPathAnimation(true)
            .setCallback(this)
            .setDestination(coordenadasCliente)
            .build
        directionutil.initPath()
    }*/
}
