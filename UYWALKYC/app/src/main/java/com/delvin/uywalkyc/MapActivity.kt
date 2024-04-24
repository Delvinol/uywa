package com.delvin.uywalkyc

import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.delvin.uywalkyc.LocacionPaseadorSchema.LocacionPaseadorResponseItem
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private var userLocationMarker: Marker? = null

    private var mMap:GoogleMap?=null
    var urlbase = "http://192.168.18.8:8080/api/v1/LocacionPaseador/"

    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode=101

    private var markerIcon: BitmapDescriptor?=null
    private var markerIcon2: BitmapDescriptor?=null
    private var markerWalker: Marker? = null
    private lateinit var btnMostrarPaseadores:Button
    private lateinit var btnOcultarPaseadores:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)

        markerIcon = getMarkerFromDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.basset)!!)
        markerIcon2 = getMarkerFromDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.iconwalker)!!)
        btnMostrarPaseadores=findViewById(R.id.btnMostrarPaseadores)
        btnOcultarPaseadores=findViewById(R.id.btnOcultarPaseadores)


        getCurrentLocationUser()
    }
    var retrofit=Retrofit.Builder()
        .baseUrl(urlbase)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service=retrofit.create(PostApiService::class.java)

    fun MostrarPaseadores(view: View) {
        lifecycleScope.launch {
            try {
                val response: Response<List<LocacionPaseadorResponseItem>> = service.getDataLocation()

                if (response.isSuccessful) {
                    val locacionPaseadores = response.body()

                    locacionPaseadores?.forEach {
                        val paseadorLocation = LatLng(it.latitud, it.longitud)
                        val markerOptions = MarkerOptions()
                            .position(paseadorLocation)
                            .title(it.nombres)
                            .icon(markerIcon2)

                        mMap?.addMarker(markerOptions)
                    }

                    toggleButtonsVisibility(false)
                } else {
                    // Manejar el caso de una respuesta no exitosa
                    Log.e("MostrarPaseadores", "Error al obtener datos de ubicación de paseadores. Código: ${response.code()}")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            applicationContext,
                            "Error al obtener datos de ubicación de paseadores. Código: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                // Manejar excepciones aquí
                Log.e("MostrarPaseadores", "Error al obtener datos de ubicación de paseadores", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        applicationContext,
                        "Error al obtener datos de ubicación de paseadores",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    private fun getCurrentLocationUser() {
        if(ActivityCompat.checkSelfPermission(
            this,android.Manifest.permission.ACCESS_FINE_LOCATION)!=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,android.Manifest.permission.ACCESS_COARSE_LOCATION)!=
            PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),permissionCode)
            return
        }

        val getLocation=fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            location ->
            if (location != null){
                currentLocation=location
                Toast.makeText(applicationContext,currentLocation.latitude.toString()+" "+
                currentLocation.longitude.toString(),Toast.LENGTH_LONG).show()

                val mapFragment=supportFragmentManager
                    .findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync(this)

            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            permissionCode -> if(grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED){
                getCurrentLocationUser()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap=googleMap
        mMap?.uiSettings?.isZoomControlsEnabled=true
        createMarker()
        mapStyle()
        /*googleMap?.isMyLocationEnabled=false
        googleMap?.isBuildingsEnabled=false*/
    }

    fun OcultarPaseadores(view: View) {
        mMap?.clear()
        markerWalker = null
        btnMostrarPaseadores.visibility = View.VISIBLE
        btnOcultarPaseadores.visibility = View.GONE
    }

    private fun toggleButtonsVisibility(showMostrar: Boolean) {
        btnMostrarPaseadores.visibility = if (showMostrar) View.VISIBLE else View.GONE
        btnOcultarPaseadores.visibility = if (!showMostrar) View.VISIBLE else View.GONE
    }

    private fun createMarker(){
        val coordinates=LatLng(currentLocation.latitude,currentLocation.longitude)
        val marker=MarkerOptions().position(coordinates).title("Ubicacion actual")
        markerWalker?.remove()
        markerWalker=mMap?.addMarker(MarkerOptions().position(coordinates).anchor(0.5f,0.5f).flat(true).icon(markerIcon))
        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates,17f))
    }

    private fun mapStyle(){
        try{
            val success = mMap?.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(this,R.raw.style)
            )
            if (!success!!){
                Log.d("MAPAS", "No se pudo encontrar el estilo")
            }
        }catch (e: Resources.NotFoundException){
            Log.d("MAPAS", "Error: ${e.toString()}")

        }
    }


    private fun addMarker(){
        val coordinates=LatLng(currentLocation.latitude,currentLocation.longitude)
        val drawable=ContextCompat.getDrawable(applicationContext,R.drawable.basset)
        val markerIcon=getMarkerFromDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.basset)!!)


        if (markerWalker == null) {
            markerWalker = mMap?.addMarker(
                MarkerOptions()
                    .position(coordinates)
                    .anchor(0.5f, 0.5f)
                    .flat(true)
                    .icon(markerIcon)
            )
        }

    }

    private fun getMarkerFromDrawable(drawable: Drawable): BitmapDescriptor {
        val canvas = Canvas()
        val bitmap = Bitmap.createBitmap(
            150,
            150,
            Bitmap.Config.ARGB_8888
        )
        canvas.setBitmap(bitmap)
        drawable.setBounds(0,0,150,150)
        drawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }



}