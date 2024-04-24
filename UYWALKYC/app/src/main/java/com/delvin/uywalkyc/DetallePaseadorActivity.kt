package com.delvin.uywalkyc



import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat

class DetallePaseadorActivity : AppCompatActivity() {
    var urlbase = "http://192.168.18.8:8080/api/v1/LocacionPaseador/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_paseador)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val ubicacion = intent.getStringExtra("ubicacion")
        val nombre = intent.getStringExtra("nombre")
        val apellido = intent.getStringExtra("apellido")
        val email = intent.getStringExtra("email")
        val descripcion = intent.getStringExtra("descripcion")
        val tarifa = intent.getStringExtra("tarifa")
        val categoriaNombre = intent.getStringExtra("categoriaNombre")
        val dni = intent.getStringExtra("dni")
        val celular=intent.getStringExtra("celular")
        val latitud=intent.getStringExtra("latitud")
        val longitud=intent.getStringExtra("longitud")
        Log.d("UBICAZAO",ubicacion.toString())
        Log.d("NOMBRE",nombre.toString())
        Log.d("APELLIDO",apellido.toString())
        Log.d("EMAIL",email.toString())
        Log.d("DESCRIPCION",descripcion.toString())
        Log.d("TARIFA",tarifa.toString())
        Log.d("EXPERIENCIA",categoriaNombre.toString())
        Log.d("DNI",dni.toString())

        val txtNombreApellido=findViewById<TextView>(R.id.txtNombre_Apellido)
        val txtEmail=findViewById<TextView>(R.id.txt_email)
        val txtUbicacion=findViewById<TextView>(R.id.txtUbicacion)
        val txtTarifa=findViewById<TextView>(R.id.txtTarifa)
        val txtCategoriaNombre=findViewById<TextView>(R.id.txtCategoriaNombre)
        val txtDNI=findViewById<TextView>(R.id.txtDNI)
        val txtDescripcion=findViewById<TextView>(R.id.txtDescripcion)
        val txtLlamada=findViewById<LinearLayout>(R.id.llamada)
        val txtMensaje=findViewById<LinearLayout>(R.id.mensaje)
        val txtMapa=findViewById<LinearLayout>(R.id.Mapa)

        txtNombreApellido.text="$nombre $apellido"
        txtEmail.text=email
        txtUbicacion.text=ubicacion
        txtTarifa.text=tarifa
        txtCategoriaNombre.text=categoriaNombre
        txtDNI.text=dni
        txtDescripcion.text=descripcion

        txtMapa.setOnClickListener {
            val intent=Intent(this,PaseadorMapaActivity::class.java)
            intent.putExtra("latitud",latitud)
            intent.putExtra("longitud",longitud)
            startActivity(intent)
        }


        txtMensaje.setOnClickListener {
            val intent = Intent(this, EnviarMailActivity::class.java)
            intent.putExtra("email", email)
            startActivity(intent)
        }


        txtLlamada.setOnClickListener {
            val celular=celular.toString()
            if(celular.isNotEmpty()){
                val callIntent=Intent(Intent.ACTION_DIAL)
                callIntent.data=Uri.parse("tel:$celular")
                startActivity(callIntent)
            }else{Toast.makeText(this,"Numero erroneo",Toast.LENGTH_SHORT).show()}
        }



    }




}