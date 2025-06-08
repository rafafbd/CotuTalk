package com.example.cotutalk_program.AcessoAPI.data
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.threeten.bp.LocalDateTime
import com.google.gson.annotations.SerializedName

@Parcelize
data class Grupo(
    @SerializedName("idGrupo")
    val IdGrupo: Int,
    @SerializedName("nome")
    val Nome: String,
    @SerializedName("dataCriacao")
    val DataCriacao: LocalDateTime,
    @SerializedName("descricao")
    val Descricao: String,
): Parcelable
