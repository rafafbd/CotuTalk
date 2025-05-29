package com.example.cotutalk_program.AcessoAPI.network

import com.example.cotutalk_program.AcessoAPI.data.Resposta
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface RespostaApi {

    @GET("/respostas")
    suspend fun listarRespostas(): List<Resposta>

    @POST("/respostas")
    suspend fun adicionarResposta(@Body resposta: Resposta): Response<Resposta>


    @DELETE("/respostas/{id}")
    suspend fun deletarResposta(@Path("id") id: Int): Response<Unit>

    @PUT("/respostas/{id}")
    suspend fun atualizarResposta(@Path("id") id: Int, @Body respostaAtualizada: Resposta): Response<Resposta>

    @GET("/respostasUsuario/{idUsuario}")
    suspend fun buscarRespostasPorUsuario(@Path("idUsuario") idUsuario: Int): Response<List<Resposta>>

    @GET("/respostasPostagem/{idPostagem}")
    suspend fun buscarRespostasPorPostagem(@Path("idPostagem") idPostagem: Int): Response<List<Resposta>>
}