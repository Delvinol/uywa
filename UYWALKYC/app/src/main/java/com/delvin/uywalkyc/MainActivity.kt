package com.delvin.uywalkyc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Se declara el btn a usar
        val btncomenzar = findViewById<TextView>(R.id.btnComenzar)

        //Se establece las funciones del botón
        btncomenzar.setOnClickListener{
            //Intent para pasar a otro activity
            val intent = Intent(this@MainActivity,LoginActivity::class.java)
            //Llamado a la acción de intent
            startActivity(intent)
        }

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }
}