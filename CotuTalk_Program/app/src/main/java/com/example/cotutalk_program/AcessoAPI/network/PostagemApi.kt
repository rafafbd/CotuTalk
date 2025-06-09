package com.example.cotutalk_program.AcessoAPI.network

import com.example.cotutalk_program.AcessoAPI.data.Postagem
import com.example.cotutalk_program.AcessoAPI.data.PostagemDTO
import com.example.cotutalk_program.AcessoAPI.data.PostagemUI
import com.example.cotutalk_program.AcessoAPI.data.Usuario
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.DELETE


interface PostagemApi {
    @GET("/postagens")
    suspend fun listarPostagens(): List<Postagem>

    @GET("/postagens/{id}")
    suspend fun buscarPostagem(@Path("id") id: Int): Postagem?

    @POST("/postagens")
    suspend fun adicionarPostagem(@Body postagem: PostagemDTO) : Postagem

    @DELETE("/postens/{id}")
    suspend fun deletarPostagem(@Path("id") id : Int) : Response<String>

    @PUT("/postagens/{id}")
    suspend fun atualizarPostagem(@Path("id") id : Int, @Body postagem: PostagemDTO) : Response<String>

    @GET("/postagensUsuario/{IdUsuario}")
    suspend fun listarPostagensUsuario(@Path("id") id : Int): List<Postagem>

    @GET("/postagensGrupo/{IdGrupo}")
    suspend fun listarPostagensGrupo(@Path("IdGrupo") id : Int): List<Postagem>
}