package com.example.cotutalk_program.AcessoAPI.data
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.threeten.bp.LocalDateTime

@Parcelize
data class Grupo(
    val IdGrupo: Int,
    val Nome: String,
    val DataCriacao: LocalDateTime,
    val Descricao: String,
): Parcelable
