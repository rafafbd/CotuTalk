package com.example.cotutalk_program.AcessoAPI.network

import com.example.cotutalk_program.AcessoAPI.data.Grupo
import com.example.cotutalk_program.AcessoAPI.data.Membro
import com.example.cotutalk_program.AcessoAPI.data.Usuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path

interface MembroApi {
    @POST("/adicionarMembro")
    suspend fun adicionarMembro(@Body membro: Membro): Membro

    @HTTP(method = "DELETE", path = "/deletarMembro", hasBody = true)
    suspend fun removerMembro(@Body membro: Membro): Response<Unit>

    @GET("/grupos/{idGrupo}/usuarios")
    suspend fun getUsuariosDoGrupo(@Path("idGrupo") idGrupo: Int): Response<List<Usuario>>

    @GET("/usuarios/{idUsuario}/grupos")
    suspend fun getGruposDoUsuario(@Path("idUsuario") idUsuario: Int): Response<List<Grupo>>
}
