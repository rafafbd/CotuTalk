package com.example.cotutalk_program.AcessoAPI.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://seu ip" // Use o endereço da sua API

    object RetrofitClient {
        private const val BASE_URL = "http://seu-ip:porta" // substitua pelo IP real

        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
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

//        val membroInstance: MembroApi by lazy {
//            retrofit.create(MembroApi::class.java)
//        }
//
//        val respostaInstance: RespostaApi by lazy {
//            retrofit.create(RespostaApi::class.java)
//        }

        val verificaoInstance: VerificacaoApi by lazy {
            retrofit.create(VerificacaoApi::class.java)
        }
    }

}