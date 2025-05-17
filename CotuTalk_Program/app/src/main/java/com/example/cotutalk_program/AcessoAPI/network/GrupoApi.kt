package com.example.cotutalk_program.AcessoAPI.network

import com.example.cotutalk_program.AcessoAPI.data.Postagem
import com.example.cotutalk_program.AcessoAPI.data.Grupo
import com.example.cotutalk_program.AcessoAPI.data.Membro
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.DELETE
import retrofit2.http.HTTP

//grupos não esta terminado

interface GrupoApi {

    @GET("/grupos")
    suspend fun listarGrupos(): List<Grupo>

    @POST("/grupos")
    suspend fun adicionarGrupo(@Body grupo: Grupo): Grupo

    @DELETE("/grupos/{id}")
    suspend fun deletarGrupo(@Path("id") id: Int): Response<Unit>

    @PUT("/grupos/{id}")
    suspend fun atualizarGrupo(@Path("id") id: Int, @Body grupo: Grupo): Response<Grupo>

    // HANDLE MEMBRO - relação N-N com Usuario

    @POST("/adicionarMembro")
    suspend fun adicionarMembro(@Body membro: Membro): Membro

    @HTTP(method = "DELETE", path = "/deletarMembro", hasBody = true) // nao da pra chamar o @delete com body, entao tem que usar http
    suspend fun deletarMembro(@Body membro: Membro): Response<String>

    // essas rotas comentadas já são inclusas em PostagemApi. São, portanto, obsoletas

//    @DELETE("/postens/{id}")
//    suspend fun deletarPostagens(@Path("id") id : Int) : Response<String>
//
//    @PUT("/postagens/{id}")
//    suspend fun atualizarPostagens(@Path("id") id : Int) : Response<String>
//
//    @GET("/postagensUsuario/{IdUsuario}")
//    suspend fun listarPostagensUsuario(@Path("id") id : Int): List<Postagem>
}