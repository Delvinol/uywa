package com.delvin.uywalkyc

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class EnviarMailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enviar_mail)
        val email = intent.getStringExtra("email")
        Log.d("CORREITO",email.toString())
        val Asunto=findViewById<EditText>(R.id.Asunto)
        val Mensaje=findViewById<EditText>(R.id.Mensaje)
        val btnEnviar=findViewById<Button>(R.id.btnEnviarEmail)

        btnEnviar.setOnClickListener{
            val email=email.toString().trim()
            val asunto=Asunto.text.toString().trim()
            val mensaje=Mensaje.text.toString().trim()
            val nIntent=Intent(Intent.ACTION_SEND)
            nIntent.data= Uri.parse("mailto:")
            nIntent.type="text/plain"
            nIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            nIntent.putExtra(Intent.EXTRA_SUBJECT,asunto)
            nIntent.putExtra(Intent.EXTRA_TEXT,mensaje)
            try{
                startActivity(Intent.createChooser(nIntent,"Enviar Correo"))
            }catch (e:Exception){
                Toast.makeText(this,e.message,Toast.LENGTH_LONG).show()
            }
        }
    }
}