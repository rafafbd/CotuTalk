package com.example.cotutalk_program.AcessoAPI.data

data class Postagem (
    val IdPostagem : Int,
    val IdUsuario : Int,
    val IdGrupo : Int,
    val Conteudo: String
//    val Curtidas: List<Curtida> = emptyList(),
//    val Respostas: List<Resposta> = emptyList()

)