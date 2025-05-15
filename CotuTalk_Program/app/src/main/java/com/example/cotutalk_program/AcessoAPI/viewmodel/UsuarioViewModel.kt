package com.example.cotutalk_program.AcessoAPI.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.cotutalk_program.AcessoAPI.data.Usuario
import com.example.cotutalk_program.AcessoAPI.network.RetrofitClient.RetrofitClient
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

    fun listarUsuarios() {
        coroutineScope.launch {
            try {
                _usuarios.value = RetrofitClient.usuarioInstance.listarUsuarios()
                _mensagem.value = "Usuários carregados com sucesso."
            } catch (e: Exception) {
                _mensagem.value = "Erro ao carregar usuários: ${e.message}"
            }
        }
    }

    fun buscarUsuario(id: Int) {
        coroutineScope.launch {
            try {
                _usuarioDetalhe.value = RetrofitClient.usuarioInstance.buscarUsuario(id)
                _mensagem.value =
                    if (_usuarioDetalhe.value != null) "Usuário encontrado." else "Usuário não encontrado."
            } catch (e: Exception) {
                _mensagem.value = "Erro ao buscar usuário: ${e.message}"
            }
        }
    }

    fun criarUsuario(nome: String, email: String, senha: String) {
        coroutineScope.launch {
            try {
                val novoUsuario = Usuario(IdUsuario = 0, Nome = nome, Email = email, Senha = senha)
                val usuarioCriado = RetrofitClient.usuarioInstance.criarUsuario(novoUsuario)
                _usuarios.value = _usuarios.value + usuarioCriado
                _mensagem.value = "Usuário criado com ID ${usuarioCriado.IdUsuario}"
            } catch (e: Exception) {
                _mensagem.value = "Erro ao criar usuário: ${e.message}"
            }
        }
    }

    fun atualizarUsuario(id: Int, nome: String, email: String, senha: String) {
        coroutineScope.launch {
            try {
                val usuarioAtualizado = Usuario(IdUsuario = id, Nome = nome, Email = email, Senha = senha)
                val response = RetrofitClient.usuarioInstance.atualizarUsuario(id, usuarioAtualizado)
                if (response.isSuccessful) {
                    _usuarios.value = _usuarios.value.map {
                        if (it.IdUsuario == id) usuarioAtualizado else it
                    }
                    _mensagem.value = "Usuário $id atualizado."
                    _usuarioDetalhe.value = usuarioAtualizado
                } else {
                    _mensagem.value = "Erro ao atualizar usuário: ${response.code()}"
                }
            } catch (e: Exception) {
                _mensagem.value = "Erro ao atualizar usuário: ${e.message}"
            }
        }
    }

    fun deletarUsuario(id: Int) {
        coroutineScope.launch {
            try {
                val response = RetrofitClient.usuarioInstance.deletarUsuario(id)
                if (response.isSuccessful) {
                    _usuarios.value = _usuarios.value.filter { it.IdUsuario != id }
                    _mensagem.value = "Usuário $id deletado."
                    _usuarioDetalhe.value = null
                } else {
                    _mensagem.value = "Erro ao deletar usuário: ${response.code()}"
                }
            } catch (e: Exception) {
                _mensagem.value = "Erro ao deletar usuário: ${e.message}"
            }
        }
    }

    var mensagemerro = _mensagem
}