package com.example.cotutalk_program.AcessoAPI.data
import org.threeten.bp.LocalDateTime

data class CodigoDeVerificacao (
    val Id : Int,
    val Email : String,
    val Codigo : String,
    val DataDeGravacao : LocalDateTime,
    val DataDeExpiracao : LocalDateTime
)