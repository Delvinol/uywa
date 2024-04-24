package com.delvin.uywalkyp

import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.location.Location
import android.location.LocationRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.delvin.uywalkyc.LoginSchema.LoginRequest
import com.delvin.uywalkyp.LocacionSchema.LocacionRequest
import com.delvin.uywalkyp.LocacionSchema.LocacionResponse
import com.delvin.uywalkyp.LoginSchema.LoginResponse
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.log

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    var urlbase = "http://192.168.18.8:8080/api/v1/LocacionPaseador/"

    private var mMap:GoogleMap?=null

    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode=101

    private var markerIcon: BitmapDescriptor?=null
    private var markerWalker: Marker? = null

    private var idLocacionPaseador: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)

        markerIcon = getMarkerFromDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.iconwalker)!!)

        /*val idPaseador=intent.getIntExtra("idPaseador",0)
        Log.d("ID DEL PASEADOR", idPaseador.toString())*/



        getCurrentLocationUser()
    }
    var retrofit = Retrofit.Builder()
        .baseUrl(urlbase)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service=retrofit.create(PostApiService::class.java)
    fun locationDB() {
        val idPaseador = intent.getIntExtra("idPaseador", 0)
        val latitud = currentLocation.latitude.toString().toDouble()
        val longitud = currentLocation.longitude.toString().toDouble()

        lifecycleScope.launch {
            try {
                val response: Response<LocacionResponse> = service.createLocacion(
                    LocacionRequest(idPaseador, latitud, longitud)
                )
                runOnUiThread {
                    if (response.isSuccessful) {
                        val result: LocacionResponse? = response.body()
                        if (result != null) {

                            intent.putExtra("idLocacionPaseador", result.idLocacionPaseador)
                            Log.d("idLocacionPaseador",result.idLocacionPaseador.toString())
                            Toast.makeText(
                                this@MapActivity,
                                "Se subió la ubicación",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.d("paseador", idPaseador.toString())
                            Log.d("latitud", latitud.toString())
                            Log.d("longitud", longitud.toString())

                        } else {
                            Toast.makeText(
                                this@MapActivity,
                                "Errorcito",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@MapActivity,
                            "Actualizado: ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@MapActivity, "Error de conexion", Toast.LENGTH_SHORT).show()
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
        val handler = Handler(Looper.getMainLooper())
        val runnable = Runnable {
            getCurrentLocationUser() // call this function again to update location
        }
        handler.postDelayed(runnable, 6000)
    }

   /* private fun startLocationUpdates() {
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                getCurrentLocationUser()
                delay(6000) // delay for 6 seconds before updating again
            }
        }
    }*/


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
        locationDB()
        /*googleMap?.isMyLocationEnabled=false
        googleMap?.isBuildingsEnabled=false*/
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
        val drawable=ContextCompat.getDrawable(applicationContext,R.drawable.iconwalker)
        val markerIcon=getMarkerFromDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.iconwalker)!!)


        if (markerWalker == null) {
            markerWalker = mMap?.addMarker(
                MarkerOptions()
                    .position(coordinates)
                    .anchor(1f, 1f)
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