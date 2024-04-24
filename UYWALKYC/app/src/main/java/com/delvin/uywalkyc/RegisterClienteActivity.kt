package com.delvin.uywalkyc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.delvin.uywalkyc.PropietarioSchema.PropietarioRequest
import com.delvin.uywalkyc.PropietarioSchema.PropietarioResponse
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterClienteActivity : AppCompatActivity() {

    var urlbase = "http://192.168.18.8:8080/api/v1/propietario/"

    private lateinit var txtPreferencias:EditText
    private lateinit var txtDistrito:Spinner
    private lateinit var txtComentario:EditText
    private lateinit var btnRegistro:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_cliente)

        txtPreferencias = findViewById(R.id.editPreferencias)
        txtComentario = findViewById(R.id.editComentario)
        btnRegistro = findViewById(R.id.button)

        val items_dis= listOf("Ancon", "Ate", "Barranco", "Brena", "Carabayllo", "Chaclacayo", "Chorrillos", "Cieneguilla", "Comas", "El Agustino", "Independencia", "Jesus Maria", "La Molina", "La Victoria", "Lince", "Los Olivos", "Lurigancho", "Lurin", "Magdalena del Mar", "Miraflores", "Pachacamac", "Pucusana", "Pueblo Libre", "Puente Piedra", "Punta Hermosa", "Punta Negra", "Rimac", "San Bartolo", "San Borja", "San Isidro", "San Juan de Lurigancho", "San Juan de Miraflores", "San Luis", "San Martin de Porres", "San Miguel", "Santa Anita", "Santa Maria del Mar", "Santa Rosa", "Santiago de Surco", "Surquillo", "Villa El Salvador", "Villa Maria del Triunfo")


        val spinner_dis:Spinner=findViewById(R.id.spinner_distrito)
        val adapter_dis=ArrayAdapter(this,android.R.layout.simple_spinner_item,items_dis)
        adapter_dis.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_dis.adapter=adapter_dis
        spinner_dis.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                val selectedItem=items_dis[position]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

    }

    var retrofit = Retrofit.Builder()
        .baseUrl(urlbase)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service=retrofit.create(PostApiService::class.java)

    fun SignUp(view: View){
        val calificacion = 100//yaesta
        val comentario = txtComentario.text.toString()
        val disponibilidad = true//ya esta
        val idUsuario = intent.getIntExtra("idUsuario", 0)
        val preferenciasPaseo = txtPreferencias.text.toString()
        val saldo = 100//yaesta
        val ubicacion = obtenerDistritoSeleccionado()


        lifecycleScope.launch {
            try {
                val response: Response<PropietarioResponse> = service.createPropietario(
                    PropietarioRequest(calificacion,comentario,disponibilidad,idUsuario,preferenciasPaseo,saldo,ubicacion)
                )
                runOnUiThread{
                    if (response.isSuccessful){
                        val result:PropietarioResponse?=response.body()
                        if (result != null){
                            val nombre = intent.getStringExtra("nombre")
                            val apellido = intent.getStringExtra("apellido")
                            val intent= Intent(this@RegisterClienteActivity,MenuActivity::class.java)
                            intent.putExtra("nombre",nombre)
                            intent.putExtra("apellido",apellido)
                            intent.putExtra("idPropietario", result.idPropietario)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this@RegisterClienteActivity,"Registro fallido", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(this@RegisterClienteActivity,"Error: ${response.code()}",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e:Exception){
                e.printStackTrace()
                runOnUiThread{
                    Toast.makeText(this@RegisterClienteActivity,"Error al registrarse", Toast.LENGTH_SHORT).show()
                }
            }
        }




    }
    private fun obtenerDistritoSeleccionado(): String {
        val spinnerDistrito: Spinner = findViewById(R.id.spinner_distrito)
        return spinnerDistrito.selectedItem.toString()
    }

}