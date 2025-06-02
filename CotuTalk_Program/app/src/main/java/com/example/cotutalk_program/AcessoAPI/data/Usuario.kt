package com.example.cotutalk_program.AcessoAPI.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Usuario(  // class "nome da tabela"
    val idUsuario : Int, // val "nome da coluna": "tipo dele"
    val nome : String,
    val email : String,
    val senha : String,
    val biografia : String,
    val imagePath : String
//    val Membros: List<Membro> = emptyList(),
//    val Postagens: List<Postagem> = emptyList(),
//    val Curtidas: List<Curtida> = emptyList(),
//    val Respostas: List<Resposta> = emptyList()

): Parcelable

// precisa de um desses para cada tabela