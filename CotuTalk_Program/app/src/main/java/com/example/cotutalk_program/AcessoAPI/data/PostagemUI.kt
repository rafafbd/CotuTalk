package com.example.cotutalk_program.AcessoAPI.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostagemUI (
    val IdPostagem : Int,
    val Conteudo: String,
    val Usuario: Usuario,
    val Grupo: Grupo,
    val qtsCurtidas: Int,
    val qtsRespostas: Int
) : Parcelable