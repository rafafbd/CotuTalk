package com.example.cotutalk_program.AcessoAPI.network

import com.example.cotutalk_program.AcessoAPI.data.Usuario
import com.example.cotutalk_program.AcessoAPI.data.LoginRequest
import com.example.cotutalk_program.AcessoAPI.data.LoginResponse
import com.google.gson.JsonArray
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.DELETE
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.Part


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
    suspend fun login(@Body login : LoginRequest) : Response<Usuario>

    @Multipart
    @POST("/upload")
    suspend fun uploadImage(@Part image: MultipartBody.Part): Response<ResponseBody>
}
