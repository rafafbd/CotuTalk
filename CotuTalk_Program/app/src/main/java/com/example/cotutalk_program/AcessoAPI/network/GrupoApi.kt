package com.example.cotutalk_program.AcessoAPI.network

import com.example.cotutalk_program.AcessoAPI.data.Postagem
import com.example.cotutalk_program.AcessoAPI.data.Grupo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.DELETE

//grupos não esta terminado

interface GrupoApi {
    @GET("/grupos")
    suspend fun listarGrupos(): List<Grupo>

    @POST("/grupos")
    suspend fun adicionarGrupos(@Body grupo: Grupo) : Response<Postagem>

    @DELETE("/postens/{id}")
    suspend fun deletarPostagens(@Path("id") id : Int) : Response<String>

    @PUT("/postagens/{id}")
    suspend fun atualizarPostagens(@Path("id") id : Int) : Response<String>

    @GET("/postagensUsuario/{IdUsuario}")
    suspend fun listarPostagensUsuario(@Path("id") id : Int): List<Postagem>

    @GET("/postagensGrupo/{IdGrupo}")
    suspend fun listarPostagensGrupo(@Path("id") id : Int): List<Postagem>
}