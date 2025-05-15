package com.example.cotutalk_program.AcessoAPI.data
import java.time.LocalDateTime

data class Grupo(
    val IdGrupo: Int,
    val Nome: String,
    val DataCriacao: LocalDateTime,
    val Descricao: String,
    //val Membros: List<Membro> = emptyList()
)
