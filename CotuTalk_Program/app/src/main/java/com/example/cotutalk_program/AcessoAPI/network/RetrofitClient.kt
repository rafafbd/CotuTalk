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
    }

}