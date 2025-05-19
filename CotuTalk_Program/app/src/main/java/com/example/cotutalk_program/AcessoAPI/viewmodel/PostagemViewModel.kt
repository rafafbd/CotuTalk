package com.example.cotutalk_program.AcessoAPI.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.cotutalk_program.AcessoAPI.data.Curtida
import com.example.cotutalk_program.AcessoAPI.data.Postagem
import com.example.cotutalk_program.AcessoAPI.data.Resposta
import com.example.cotutalk_program.AcessoAPI.network.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



class PostagemViewModel : ViewModel() {
    private val _postagens = mutableStateOf<List<Postagem>>(emptyList())
    val postagens: State<List<Postagem>> = _postagens

    private val _postagemDetalhe = mutableStateOf<Postagem?>(null)
    val usuarioDetalhe: State<Postagem?> = _postagemDetalhe

    private val _mensagem = mutableStateOf("")
    val mensagem: State<String> = _mensagem
    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    private val _curtidasPostagem = mutableStateOf<List<Curtida>>(emptyList())
    val curtidasPostagem: State<List<Curtida>> = _curtidasPostagem

    private val _respostasPostagem = mutableStateOf<List<Resposta>>(emptyList())
    val respostasPostagem: State<List<Resposta>> = _respostasPostagem

    fun listarPostagens() {
        coroutineScope.launch {
            try {
                _postagens.value = ApiService.postagemInstance.listarPostagens()
                _mensagem.value = "Usuários carregados com sucesso."
            } catch (e: Exception) {
                _mensagem.value = "Erro ao carregar usuários: ${e.message}"
            }
        }
    }

    fun buscarPostagem(id: Int) {
        coroutineScope.launch {
            try {
                _postagemDetalhe.value = ApiService.postagemInstance.buscarPostagem(id)
                _mensagem.value =
                    if (_postagemDetalhe.value != null) "Usuário encontrado." else "Usuário não encontrado."
            } catch (e: Exception) {
                _mensagem.value = "Erro ao buscar usuário: ${e.message}"
            }
        }
    }

    fun adicionarPostagem(idp: Int, idu: Int, idg: Int, conteudo: String) {
        coroutineScope.launch {
            try {
                val novaPostagem = Postagem(IdPostagem = idp, IdUsuario = idu, IdGrupo = idg, Conteudo = conteudo)
                val postagemAtualizada = ApiService.postagemInstance.adicionarPostagem(novaPostagem)
                _postagens.value = _postagens.value + postagemAtualizada
                _mensagem.value = "Postagem criada com ID ${postagemAtualizada.IdPostagem}"
            } catch (e: Exception) {
                _mensagem.value = "Erro ao criar usuário: ${e.message}"
            }
        }
    }

    fun atualizarPostagem(idp: Int, idu: Int, idg: Int, conteudo: String) {
        coroutineScope.launch {
            try {
                val postagemAtualizada = Postagem(IdPostagem = idp, IdUsuario = idu, IdGrupo = idg, Conteudo = conteudo)
                val response = ApiService.postagemInstance.atualizarPostagem(idp, postagemAtualizada)
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

    fun deletarPostagem(id: Int) {
        coroutineScope.launch {
            try {
                val response = ApiService.postagemInstance.deletarPostagem(id)
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

    //HANDLER CURTIDA
    fun adicionarCurtida(idCurtida: Int, idUsuario: Int, idPostagem: Int) {
        coroutineScope.launch {
            try {
                val novaCurtida = Curtida(
                    IdCurtida = idCurtida,
                    IdUsuario = idUsuario,
                    IdPostagem = idPostagem
                )
                val curtidaCriada = ApiService.curtidaInstance.adicionarCurtida(novaCurtida)
                _mensagem.value = "Curtida adicionada com sucesso (ID ${curtidaCriada.IdCurtida})"
            } catch (e: Exception) {
                _mensagem.value = "Erro ao adicionar curtida: ${e.message}"
            }
        }
    }

    fun listarCurtidas() {
        coroutineScope.launch {
            try {
                val curtidas = ApiService.curtidaInstance.listarCurtidas()
                _mensagem.value = "Curtidas carregadas com sucesso (${curtidas.size})"
                _curtidasPostagem.value = curtidas
            } catch (e: Exception) {
                _mensagem.value = "Erro ao listar curtidas: ${e.message}"
            }
        }
    }

    fun buscarCurtidasPorPostagem(idPostagem: Int) {
        coroutineScope.launch {
            try {
                val curtidas = ApiService.curtidaInstance.buscarCurtidasPorPostagem(idPostagem)
                _mensagem.value = "Curtidas encontradas para o post $idPostagem: ${curtidas.size}"
                _curtidasPostagem.value = curtidas
            } catch (e: Exception) {
                _mensagem.value = "Erro ao buscar curtidas do post: ${e.message}"
            }
        }
    }

    //HANDLE RESPOSTA
    fun buscarRespostasPorPostagem(idPostagem: Int) {
        coroutineScope.launch {
            try {
                val response = ApiService.respostaInstance.buscarRespostasPorPostagem(idPostagem)
                if (response.isSuccessful) {
                    _respostasPostagem.value = response.body() ?: emptyList()
                } else {
                    _mensagem.value = "Erro ao buscar respostas da postagem"
                }
            } catch (e: Exception) {
                _mensagem.value = "Exceção: ${e.message}"
            }
        }
    }

}
