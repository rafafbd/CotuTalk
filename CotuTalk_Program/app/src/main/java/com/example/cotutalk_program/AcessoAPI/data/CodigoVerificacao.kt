package com.example.cotutalk_program.AcessoAPI.data
import java.time.LocalDateTime

data class CodigoVerificacao(
    val Id: Int,
    val Email: String,
    val Codigo: String,
    val DataDeGeracao: LocalDateTime,
    val DataDeExpiracao: LocalDateTime
)
