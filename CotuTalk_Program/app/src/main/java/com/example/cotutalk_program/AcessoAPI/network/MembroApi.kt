package com.example.cotutalk_program.AcessoAPI.network

import com.example.cotutalk_program.AcessoAPI.data.Membro
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.HTTP
import retrofit2.http.POST

interface MembroApi {
    @POST("/adicionarMembro")
    suspend fun adicionarMembro(@Body membro: Membro): Membro

    @HTTP(method = "DELETE", path = "/deletarMembro", hasBody = true)
    suspend fun removerMembro(@Body membro: Membro): Response<Unit>
}
