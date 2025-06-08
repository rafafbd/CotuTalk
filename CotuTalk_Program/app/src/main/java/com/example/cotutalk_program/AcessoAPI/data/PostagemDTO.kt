package com.example.cotutalk_program.AcessoAPI.data

import com.google.gson.annotations.SerializedName

data class PostagemDTO (
    @SerializedName("idUsuario")
    val IdUsuario: Int,
    @SerializedName("idGrupo")
    val IdGrupo: Int,
    @SerializedName("conteudo")
    val Conteudo: String
)