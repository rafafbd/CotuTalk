package com.example.cotutalk_program.AcessoAPI.data
import org.threeten.bp.LocalDateTime

data class CodigoVerificacao(
    val Id: Int,
    val Email: String,
    val Codigo: String,
    val DataDeGeracao: LocalDateTime,
    val DataDeExpiracao: LocalDateTime
)
