package com.example.cotutalk_program.AcessoAPI.data

data class PostagemUI (
    val IdPostagem : Int,
    val Conteudo: String,
    val Usuario: Usuario,
    val Grupo: Grupo,
    val qtsCurtidas: Int,
    val qtsRespostas: Int
)