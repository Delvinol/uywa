package com.delvin.uywalkyp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.delvin.uywalkyp.PaseadorSchema.PaseadorRequest
import com.delvin.uywalkyp.PaseadorSchema.PaseadorResponse
import com.delvin.uywalkyp.RegisterSchema.RegisterRequest
import com.delvin.uywalkyp.RegisterSchema.RegisterResponse
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterPaseadorActivity : AppCompatActivity() {
    var urlbase = "http://192.168.18.8:8080/api/v1/paseador/"
    private lateinit var txtCategoria:Spinner
    private lateinit var txtTarifa:EditText
    private lateinit var txtDescripcion:EditText
    private lateinit var txtDistrito:Spinner
    private lateinit var btnRegistro:Button

    private var idUsuario: Int = 0
    private val categoriaMap = mapOf("Experto" to 1, "Novato" to 2, "Exótico" to 3)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_paseador)

        txtDescripcion=findViewById(R.id.editDescripcion)
        txtTarifa=findViewById(R.id.edit_tarifa)
        btnRegistro=findViewById(R.id.button)




        val spinner:Spinner=findViewById(R.id.spinner_categoria)
        val items_cat= listOf("Experto", "Novato", "Exótico")
        val items_dis= listOf("Ancon", "Ate", "Barranco", "Brena", "Carabayllo", "Chaclacayo", "Chorrillos", "Cieneguilla", "Comas", "El Agustino", "Independencia", "Jesus Maria", "La Molina", "La Victoria", "Lince", "Los Olivos", "Lurigancho", "Lurin", "Magdalena del Mar", "Miraflores", "Pachacamac", "Pucusana", "Pueblo Libre", "Puente Piedra", "Punta Hermosa", "Punta Negra", "Rimac", "San Bartolo", "San Borja", "San Isidro", "San Juan de Lurigancho", "San Juan de Miraflores", "San Luis", "San Martin de Porres", "San Miguel", "Santa Anita", "Santa Maria del Mar", "Santa Rosa", "Santiago de Surco", "Surquillo", "Villa El Salvador", "Villa Maria del Triunfo")

        val adapter_cat=ArrayAdapter(this,android.R.layout.simple_spinner_item,items_cat)
        adapter_cat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter=adapter_cat
        spinner.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                val selectedItem=items_cat[position]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

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
    private fun obtenerIdCategoria(): Int {
        val spinnerCategoria: Spinner = findViewById(R.id.spinner_categoria)
        val categoriaSeleccionada = spinnerCategoria.selectedItem.toString()

        // Obtén el idCategoria del mapa
        return categoriaMap[categoriaSeleccionada] ?: 0
    }

    var retrofit = Retrofit.Builder()
        .baseUrl(urlbase)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service=retrofit.create(PostApiService::class.java)

    fun SignUp(view: View){
        val calificacion = 100//yaesta
        val descripcion = txtDescripcion.text.toString()//ya esta
        val disponibilidad = true//ya esta
        val experiencia = "null"//yaesta
        val idCategoria = obtenerIdCategoria()
        val idUsuario = intent.getIntExtra("idUsuario", 0)
        val saldo = 100//yaesta
        val tarifa = txtTarifa.text.toString().toDouble()//yaesta000
        val ubicacion = obtenerDistritoSeleccionado()

        lifecycleScope.launch {
            try {
                val response: Response<PaseadorResponse> = service.createPaseador(PaseadorRequest(calificacion,descripcion,disponibilidad,experiencia,idCategoria,idUsuario,saldo, tarifa,ubicacion))
                runOnUiThread{
                    if (response.isSuccessful){
                        val result:PaseadorResponse?=response.body()
                        if (result != null){
                            val nombre = intent.getStringExtra("nombre")
                            val apellido = intent.getStringExtra("apellido")
                            val intent=Intent(this@RegisterPaseadorActivity,MenuActivity::class.java)
                            intent.putExtra("nombre",nombre)
                            intent.putExtra("apellido",apellido)
                            intent.putExtra("idPaseador", result.idPaseador)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this@RegisterPaseadorActivity,"Registro fallido", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(this@RegisterPaseadorActivity,"Error: ${response.code()}",Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e:Exception){
                e.printStackTrace()
                runOnUiThread{
                    Toast.makeText(this@RegisterPaseadorActivity,"Error al registrarse",Toast.LENGTH_SHORT).show()
                }
            }
        }




    }
    private fun obtenerDistritoSeleccionado(): String {
        val spinnerDistrito: Spinner = findViewById(R.id.spinner_distrito)
        return spinnerDistrito.selectedItem.toString()
    }
}