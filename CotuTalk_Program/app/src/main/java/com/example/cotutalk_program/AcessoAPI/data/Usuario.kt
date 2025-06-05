package com.example.cotutalk_program.AcessoAPI.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Usuario(
    val idUsuario : Int,
    val nome : String,
    val email : String,
    val senha : String,
    val biografia : String,
    val imagePath : String
): Parcelable
