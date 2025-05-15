package com.example.cotutalk_program.AcessoAPI.data
import java.time.LocalDateTime

data class Resposta(
    val IdResposta: Int,
    val IdPostagem: Int,
    val IdUsuario: Int,
    val Conteudo: String,
    val DataComentario: LocalDateTime
)
