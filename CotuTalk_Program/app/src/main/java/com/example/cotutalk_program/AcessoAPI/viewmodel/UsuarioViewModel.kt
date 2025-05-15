package com.example.cotutalk_program.AcessoAPI.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.cotutalk_program.AcessoAPI.data.Usuario
import com.example.clienteapp.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsuarioViewModel : ViewModel() {
    private val _usuarios = mutableStateOf<List<Usuario>>(emptyList())
    val usuarios: State<List<Usuario>> = _usuarios

    private val _usuarioDetalhe = mutableStateOf<Usuario?>(null)
    val usuarioDetalhe: State<Usuario?> = _usuarioDetalhe

    private val _mensagem = mutableStateOf("")
    val mensagem: State<String> = _mensagem
    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    fun buscaUsuarioPorId(id: String){

    }
}