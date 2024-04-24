package com.delvin.uywalkyc

import com.delvin.uywalkyc.LocacionPaseadorSchema.LocacionPaseadorResponseItem
import com.delvin.uywalkyc.LoginSchema.LoginRequest
import com.delvin.uywalkyc.LoginSchema.LoginResponse
import com.delvin.uywalkyc.PaseadoresSchema.PaseadorResponseItem
import com.delvin.uywalkyc.PropietarioSchema.PropietarioRequest
import com.delvin.uywalkyc.PropietarioSchema.PropietarioResponse
import com.delvin.uywalkyc.RegisterSchema.RegisterRequest
import com.delvin.uywalkyc.RegisterSchema.RegisterResponse
import com.delvin.uywalkyc.RouteSchema.RouteResponse
import com.delvin.uywalkyc.UsuariosSchema.PostModelResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PostApiService {

    //Para ver todos los usuarios
    @GET("user")
    suspend fun getUserPost():ArrayList<PostModelResponse>

    //Para ver un usuario con un ID especifico
    @GET("user/{id}")
    suspend fun getUserPostById(@Path("id") id:String):ArrayList<PostModelResponse>

    //Para logearse
    @POST("authenticate")
    suspend fun getUserLogin(@Body loginResquest: LoginRequest): Response<LoginResponse>

    //Para poder registrar usuarios
    @POST("register")
    suspend fun createUser(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    @POST("register")
    suspend fun createPropietario(@Body propietarioRequest: PropietarioRequest): Response<PropietarioResponse>

    /*@POST("register")
    suspend fun createLocacion(@Body )*/

    @GET("findAllPaseadores")
    fun getData(): Call<List<PaseadorResponseItem>>

    @GET("findAllLocacionPaseadores")
    suspend fun getDataLocation(): Response<List<LocacionPaseadorResponseItem>>

    @GET("v2/directions/driving-car")
    suspend fun getRoute(@Query("api_key") key:String,
                         @Query("start", encoded = true) start: String,
                         @Query("end", encoded = true) end: String
    ):Response<RouteResponse>
}