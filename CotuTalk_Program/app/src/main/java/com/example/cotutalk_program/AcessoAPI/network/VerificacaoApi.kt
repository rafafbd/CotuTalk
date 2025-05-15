package com.example.cotutalk_program.AcessoAPI.network

import com.example.cotutalk_program.AcessoAPI.data.EmailRequest
import com.example.cotutalk_program.AcessoAPI.data.ValidarCodigoRequest
import com.example.cotutalk_program.AcessoAPI.data.CodigoDeVerificacao
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.DELETE


interface VerificacaoApi {

    @POST("/enviar-email")
    suspend fun enviarEmail(@Body email : EmailRequest) : Response<String>

    @GET("/codigos-de-verificacao")
    suspend fun listarCodigosDeVerificacao() : List<CodigoDeVerificacao>

    @POST("/validar-codigo")
    suspend fun validarCodigo(@Body codigo : ValidarCodigoRequest) : Response<String>

}