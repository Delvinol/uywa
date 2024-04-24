package com.delvin.uywalkyc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.delvin.uywalkyc.PaseadoresSchema.PaseadorResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListaPaseadorActivity : AppCompatActivity() {

    lateinit var rvMain:RecyclerView
    lateinit var myAdapter: MyAdapter

    var urlbase="http://192.168.18.8:8080/api/v1/paseador/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_paseador)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        rvMain=findViewById(R.id.recycler_view)
        rvMain.layoutManager=LinearLayoutManager(this)

        getAllData()

    }

    private fun irDetalle(){
        val btnDetalle=findViewById<Button>(R.id.btnDetalle)
        val intent = Intent(this@ListaPaseadorActivity, DetallePaseadorActivity::class.java)
        startActivity(intent)
    }

    private fun getAllData() {
        var retrofit=Retrofit.Builder()
            .baseUrl(urlbase)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostApiService::class.java)


        var retroData = retrofit.getData()

        retroData.enqueue(object : Callback<List<PaseadorResponseItem>>{
            override fun onResponse(
                call: Call<List<PaseadorResponseItem>>,
                response: Response<List<PaseadorResponseItem>>
            ) {
                var data = response.body()!!

                // En la implementación, pasa los datos que deseas a DetallePaseadorActivity
                myAdapter = MyAdapter(baseContext, object : OnDetalleClickListener {
                    override fun onDetalleClick(
                        position: Int, ubicacion: String, nombre: String,
                        apellidos:String,
                        email:String,
                        descripcion:String,
                        tarifa: Double,
                        categoriaNombre:String,
                        dni:String, celular:String,latitud:String,longitud:String) {
                        val intent = Intent(this@ListaPaseadorActivity, DetallePaseadorActivity::class.java)
                        intent.putExtra("ubicacion", ubicacion)
                        intent.putExtra("nombre", nombre)
                        intent.putExtra("apellido",apellidos)
                        intent.putExtra("email",email)
                        intent.putExtra("descripcion",descripcion)
                        intent.putExtra("tarifa",tarifa.toString())
                        intent.putExtra("categoriaNombre",categoriaNombre)
                        intent.putExtra("dni",dni)
                        intent.putExtra("celular",celular)
                        intent.putExtra("latitud",latitud)
                        intent.putExtra("longitud",longitud)
                        startActivity(intent)
                    }
                }, data)

                rvMain.adapter=myAdapter

                Log.d("data",data.toString())

            }

            override fun onFailure(call: Call<List<PaseadorResponseItem>>, t: Throwable) {

            }

        })


    }


}