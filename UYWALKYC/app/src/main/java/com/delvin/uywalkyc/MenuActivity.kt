package com.delvin.uywalkyc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        OpenMap()
        OpenProfile()
        showNameMenu()
        OpenWalkers()


        val btnCerrarSesion = findViewById<LinearLayout>(R.id.logoutIcon)
        btnCerrarSesion.setOnClickListener {
            cerrarSesion()
        }
    }
    private fun cerrarSesion() {
        val sharedPref = getSharedPreferences("login_info", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.clear()
        editor.apply()

        // Luego, redirigir a MainActivity
        val intent = Intent(this@MenuActivity, MainActivity::class.java)
        startActivity(intent)
        finish() // Cierra MenuActivity para que no vuelva atrás
    }

    private fun showNameMenu(){
        val extras = intent.extras
        if (extras!=null){
            val nombre=extras.getString("nombre")?.capitalize()
            val apellido=extras.getString("apellido")?.capitalize()
            val nombreCompleto = "$nombre $apellido"
            val txtName=findViewById<TextView>(R.id.txtNombre)
            txtName.text=nombreCompleto
        }
    }

    private fun OpenMap(){
        val txtMap = findViewById<LinearLayout>(R.id.MapIcon)
        txtMap.setOnClickListener{
            val idPropietario=intent.getIntExtra("idPropietario",0)
            if (idPropietario != 0){
                val intent=Intent(this@MenuActivity,MapActivity::class.java)
                intent.putExtra("idPropietario",idPropietario)
                startActivity(intent)
            }else{
                Toast.makeText(this@MenuActivity,"Debes llenar tu cuenta propietario",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun OpenProfile(){
        val txtProfile=findViewById<LinearLayout>(R.id.ProfileIcon)
        txtProfile.setOnClickListener {
            val idUsuario=intent.getIntExtra("idUsuario",0)
            val nombre = intent.getStringExtra("nombre")
            val apellido = intent.getStringExtra("apellido")
            val intentr=Intent(this@MenuActivity,RegisterClienteActivity::class.java)
            intentr.putExtra("idUsuario",idUsuario)
            intentr.putExtra("nombre",nombre)
            intentr.putExtra("apellido",apellido)
            Log.d("A LOG IDUSUARIO",idUsuario.toString())
            Log.d("A LOG name",nombre.toString())
            Log.d("A LOG apellido",apellido.toString())
            startActivity(intentr)
        }
    }


    private fun OpenWalkers(){
        val txtWalker=findViewById<LinearLayout>(R.id.WalkersIcon)
        txtWalker.setOnClickListener {
            val intent=Intent(this@MenuActivity,ListaPaseadorActivity::class.java)
            startActivity(intent)
        }
    }

}