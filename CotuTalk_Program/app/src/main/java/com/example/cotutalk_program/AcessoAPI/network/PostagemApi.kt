package com.example.clienteapp.network

import com.example.cotutalk_program.AcessoAPI.data.Postagem

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.DELETE


interface PostagemApi {
    @GET("/postagens")
    suspend fun listarPostagens(): List<Postagem>
/*
    @GET("/usuarios/{id}")
    suspend fun buscarUsuario(@Path("id") id: Int): Usuario?

    @POST("/usuarios")
    suspend fun criarUsuario(@Body usuario: Usuario): Usuario

    @PUT("/usuarios/{id}")
    suspend fun atualizarUsuario(@Path("id") id: Int, @Body usuario: Usuario): retrofit2.Response<Unit>

    @DELETE("/usuarios/{id}")
    suspend fun deletarUsuario(@Path("id") id: Int): retrofit2.Response<Unit>
    */
}