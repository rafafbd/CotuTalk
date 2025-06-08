package com.example.cotutalk_program.AcessoAPI.data

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime

data class Postagem (
    @SerializedName("idPostagem")
    val IdPostagem : Int,
    @SerializedName("idUsuario")
    val IdUsuario : Int,
    @SerializedName("idGrupo")
    val IdGrupo : Int,
    @SerializedName("conteudo")
    val conteudo: String,
    @SerializedName("dataCriacao")
    val dataCriacao: LocalDateTime,
    @SerializedName("grupo")
    val Grupo: Grupo,
    @SerializedName("usuario")
    val Usuario: Usuario
)