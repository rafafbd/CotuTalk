package com.example.cotutalk_program.AcessoAPI.data

import com.google.gson.annotations.SerializedName

data class Postagem (
    @SerializedName("idPostagem")
    val IdPostagem : Int,
    @SerializedName("idUsuario")
    val IdUsuario : Int,
    @SerializedName("idGrupo")
    val IdGrupo : Int,
    @SerializedName("conteudo")
    val Conteudo: String
//    val Grupo: Grupo,
//    val Usuario: Usuario
)