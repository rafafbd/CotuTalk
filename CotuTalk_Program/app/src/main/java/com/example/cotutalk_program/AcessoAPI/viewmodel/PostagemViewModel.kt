package com.example.cotutalk_program.AcessoAPI.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.cotutalk_program.AcessoAPI.data.Postagem
import com.example.cotutalk_program.AcessoAPI.network.RetrofitClient.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostagemViewModel {

    class UsuarioViewModel : ViewModel() {
        private val _postagens = mutableStateOf<List<Postagem>>(emptyList())
        val postagens: State<List<Postagem>> = _postagens

        private val _postagemDetalhe = mutableStateOf<Postagem?>(null)
        val usuarioDetalhe: State<Postagem?> = _postagemDetalhe

        private val _mensagem = mutableStateOf("")
        val mensagem: State<String> = _mensagem
        private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

        fun listarUsuarios() {
            coroutineScope.launch {
                try {
                    _postagens.value = RetrofitClient.postagemInstance.listarPostagens()
                    _mensagem.value = "Usuários carregados com sucesso."
                } catch (e: Exception) {
                    _mensagem.value = "Erro ao carregar usuários: ${e.message}"
                }
            }
        }

        fun buscarUsuario(id: Int) {
            coroutineScope.launch {
                try {
                    _postagemDetalhe.value = RetrofitClient.postagemInstance.buscarPostagem(id)
                    _mensagem.value =
                        if (_postagemDetalhe.value != null) "Usuário encontrado." else "Usuário não encontrado."
                } catch (e: Exception) {
                    _mensagem.value = "Erro ao buscar usuário: ${e.message}"
                }
            }
        }

        fun criarUsuario(nome: String, email: String, senha: String) {
            coroutineScope.launch {
                try {
                    val novaPostagem = Postagem(IdUsuario = 0, Nome = nome, Email = email, Senha = senha)
                    val usuarioCriado = RetrofitClient.postagemInstance.criarPostagem(novaPostagem)
                    _postagens.value = _postagens.value + usuarioCriado
                    _mensagem.value = "Usuário criado com ID ${usuarioCriado.IdUsuario}"
                } catch (e: Exception) {
                    _mensagem.value = "Erro ao criar usuário: ${e.message}"
                }
            }
        }

        fun atualizarUsuario(idp: Int, idu: Int, idg: Int, conteudo: String) {
            coroutineScope.launch {
                try {
                    val postagemAtualizada = Postagem(IdPostagem = idp, IdUsuario = idu, IdGrupo = idg, Conteudo = conteudo)
                    val response = RetrofitClient.postagemInstance.atualizarPostagem(IdPostagem, postagemAtualizada)
                    if (response.isSuccessful) {
                        _postagens.value = _postagens.value.map {
                            if (it.IdPostagem == idp) postagemAtualizada else it
                        }
                        _mensagem.value = "Postagem $idp atualizada."
                        _postagemDetalhe.value = postagemAtualizada
                    } else {
                        _mensagem.value = "Erro ao atualizar postagem: ${response.code()}"
                    }
                } catch (e: Exception) {
                    _mensagem.value = "Erro ao atualizar postagem: ${e.message}"
                }
            }
        }

        fun deletarUsuario(id: Int) {
            coroutineScope.launch {
                try {
                    val response = RetrofitClient.postagemInstance.deletarPostagem(id)
                    if (response.isSuccessful) {
                        _postagens.value = _postagens.value.filter { it.IdPostagem != id }
                        _mensagem.value = "Postagem $id deletada."
                        _postagemDetalhe.value = null
                    } else {
                        _mensagem.value = "Erro ao deletar postagem: ${response.code()}"
                    }
                } catch (e: Exception) {
                    _mensagem.value = "Erro ao deletar postagem: ${e.message}"
                }
            }
        }

        var mensagemerro = _mensagem
    }
}