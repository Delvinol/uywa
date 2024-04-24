package com.delvin.uywalkyc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.delvin.uywalkyc.RegisterSchema.RegisterRequest
import com.delvin.uywalkyc.RegisterSchema.RegisterResponse
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {

    var urlbase = "http://192.168.18.8:8080/api/v1/auth/"
    private lateinit var txtName: EditText
    private lateinit var txtLastname: EditText
    private lateinit var txtDNI: EditText
    private lateinit var txtPhone: EditText
    private lateinit var txtEmail:EditText
    private lateinit var txtAge:EditText
    private lateinit var txtPassword:EditText
    private lateinit var txtRePassword: EditText
    private lateinit var btnSignup:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        txtName=findViewById(R.id.input_name)
        txtLastname=findViewById(R.id.input_lastname)
        txtDNI=findViewById(R.id.input_dni)
        txtPhone=findViewById(R.id.input_phone)
        txtEmail=findViewById(R.id.input_email)
        txtAge=findViewById(R.id.input_age)
        txtPassword=findViewById(R.id.input_password)
        txtRePassword=findViewById(R.id.input_rpassword)
        btnSignup=findViewById(R.id.btn_signup)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    var retrofit = Retrofit.Builder()
        .baseUrl(urlbase)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service=retrofit.create(PostApiService::class.java)




    fun SignUp(view: View) {
        val apellidos = txtLastname.text.toString()
        val apodo = ""
        val celular = txtPhone.text.toString()
        val direccion = ""
        val dni = txtDNI.text.toString()
        val edad = txtAge.text.toString().toInt()
        val email = txtEmail.text.toString()
        val nombres = txtName.text.toString()
        val password = txtPassword.text.toString()
        val tipoUsuarioId = 1
        val repassword = txtRePassword.text.toString()


        if (password != repassword) {
            Toast.makeText(this@RegisterActivity, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                val response: Response<RegisterResponse> = service.createUser(RegisterRequest(apellidos,apodo,celular,direccion,dni,edad, email, nombres, password, tipoUsuarioId))
                runOnUiThread {
                    if (response.isSuccessful) {
                        val result: RegisterResponse? = response.body()
                        if (result?.token != null) { // Check if token is not null
                            Log.d("Token", result.token)
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@RegisterActivity, "Registro fallido", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@RegisterActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this@RegisterActivity, "Error al registrarse", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }




}