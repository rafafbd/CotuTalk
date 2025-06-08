package com.example.cotutalk_program.AcessoAPI.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.threeten.bp.LocalDateTime

object ApiService {
    const val BASE_URL = "http://10.0.2.2:5206/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Ou .HEADERS para menos detalhes
    }

    // Crie o cliente OkHttp com o interceptor de log
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    // Crie uma instância de Gson com o TypeAdapter registrado
    private val gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter()) // Registre o adapter para LocalDateTime
        .create()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient) // Use o cliente OkHttp com o interceptor
            .addConverterFactory(GsonConverterFactory.create(gson)) // Use a instância de Gson personalizada aqui!
            .build()
    }

    val usuarioInstance: UsuarioApi by lazy {
        retrofit.create(UsuarioApi::class.java)
    }

    val postagemInstance: PostagemApi by lazy {
        retrofit.create(PostagemApi::class.java)
    }

    val curtidaInstance: CurtidaApi by lazy {
        retrofit.create(CurtidaApi::class.java)
    }

    val grupoInstance: GrupoApi by lazy {
        retrofit.create(GrupoApi::class.java)
    }

    val membroInstance: MembroApi by lazy {
        retrofit.create(MembroApi::class.java)
    }

    val respostaInstance: RespostaApi by lazy {
        retrofit.create(RespostaApi::class.java)
    }

    val verificaoInstance: VerificacaoApi by lazy {
        retrofit.create(VerificacaoApi::class.java)
    }
}