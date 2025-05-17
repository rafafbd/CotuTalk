package com.example.cotutalk_program.AcessoAPI.network

import com.example.cotutalk_program.AcessoAPI.data.Curtida
import com.google.gson.JsonArray
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.DELETE
import retrofit2.Response

interface CurtidaApi {

    @POST("/curtidas")
    suspend fun adicionarCurtida(@Body curtida: Curtida): Curtida

    @GET("/curtidas")
    suspend fun listarCurtidas(): List<Curtida>

    @DELETE("/curtidas/usuario/{idUsuario}")
    suspend fun deletarCurtidaPorUsuario(@Path("idUsuario") idUsuario: Int): Response<Unit>

    @GET("/curtidasUsuario/{idUsuario}")
    suspend fun buscarCurtidasPorUsuario(@Path("idUsuario") idUsuario: Int): List<Curtida>

    @GET("/curtidasPostagem/{idPostagem}")
    suspend fun buscarCurtidasPorPostagem(@Path("idPostagem") idPostagem: Int): List<Curtida>
}


