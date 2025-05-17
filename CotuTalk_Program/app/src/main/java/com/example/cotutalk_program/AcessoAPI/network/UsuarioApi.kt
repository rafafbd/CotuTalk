package com.example.cotutalk_program.AcessoAPI.network

import com.example.cotutalk_program.AcessoAPI.data.Usuario
import com.example.cotutalk_program.AcessoAPI.data.LoginRequest
import com.example.cotutalk_program.AcessoAPI.data.LoginResponse
import com.google.gson.JsonArray
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.DELETE
import retrofit2.Response


interface UsuarioApi {
    @GET("/usuarios")
    suspend fun listarUsuarios(): List<Usuario>

    @GET("/usuarios/{id}")
    suspend fun buscarUsuario(@Path("id") id: Int): Usuario?

    @POST("/usuarios")
    suspend fun adicionarUsuario(@Body usuario: Usuario): Usuario

    @PUT("/usuarios/{id}")
    suspend fun atualizarUsuario(@Path("id") id: Int, @Body usuario: Usuario): Response<String>

    @DELETE("/usuarios/{id}")
    suspend fun deletarUsuario(@Path("id") id: Int): Response<String>

    @POST("/login")
    suspend fun login(@Body login : LoginRequest) : Response<LoginResponse>
}
