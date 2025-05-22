package com.example.cotutalk_program.AcessoAPI.data
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Grupo(
    val IdGrupo: Int,
    val Nome: String,
    val DataCriacao: LocalDateTime,
    val Descricao: String,
    //val Membros: List<Membro> = emptyList()
): Parcelable
