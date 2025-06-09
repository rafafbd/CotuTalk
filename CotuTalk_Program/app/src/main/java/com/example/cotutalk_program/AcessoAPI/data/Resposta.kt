package com.example.cotutalk_program.AcessoAPI.data
import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime

data class Resposta(
    val IdResposta: Int,
    val IdPostagem: Int,
    val IdUsuario: Int,
    @SerializedName("conteudo")
    val Conteudo: String,
    val DataComentario: LocalDateTime?
)
