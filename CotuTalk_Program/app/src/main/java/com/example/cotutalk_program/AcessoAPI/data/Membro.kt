package com.example.cotutalk_program.AcessoAPI.data
import org.threeten.bp.LocalDateTime

data class Membro(
    val IdMembro: Int,
    val IdUsuario: Int,
    val Idgrupo: Int,
    val DataDeEntrada: LocalDateTime
)
