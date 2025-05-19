package com.example.cotutalk_program.AcessoAPI.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.cotutalk_program.AcessoAPI.data.Grupo
import com.example.cotutalk_program.AcessoAPI.data.Membro
import com.example.cotutalk_program.AcessoAPI.network.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class GrupoViewModel : ViewModel() {
    private val _grupos = mutableStateOf<List<Grupo>>(emptyList())
    val grupos: State<List<Grupo>> = _grupos

    private val _mensagem = mutableStateOf("")
    val mensagem: State<String> = _mensagem
    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    fun listarGrupos() {
        coroutineScope.launch {
            try {
                _grupos.value = ApiService.grupoInstance.listarGrupos()
                _mensagem.value = "Grupos carregados com sucesso."
            } catch (e: Exception) {
                _mensagem.value = "Erro ao carregar grupos: ${e.message}"
            }
        }
    }

    fun adicionarGrupo(nome: String, descricao: String, dt: LocalDateTime) {
        coroutineScope.launch {
            try {
                val novoGrupo = Grupo(IdGrupo = 0, Nome = nome, Descricao = descricao, DataCriacao = dt)
                val grupoCriado = ApiService.grupoInstance.adicionarGrupo(novoGrupo)
                _grupos.value = _grupos.value + grupoCriado
                _mensagem.value = "Grupo criado com ID ${grupoCriado.IdGrupo}"
            } catch (e: Exception) {
                _mensagem.value = "Erro ao adicionar grupo: ${e.message}"
            }
        }
    }

    fun deletarGrupo(id: Int) {
        coroutineScope.launch {
            try {
                val response = ApiService.grupoInstance.deletarGrupo(id)
                if (response.isSuccessful) {
                    _grupos.value = _grupos.value.filter { it.IdGrupo != id }
                    _mensagem.value = "Grupo $id deletado com sucesso."
                } else {
                    _mensagem.value = "Erro ao deletar grupo: ${response.code()}"
                }
            } catch (e: Exception) {
                _mensagem.value = "Erro ao deletar grupo: ${e.message}"
            }
        }
    }

    fun atualizarGrupo(id: Int, nome: String, descricao: String, dt: LocalDateTime) {
        coroutineScope.launch {
            try {
                val grupoAtualizado = Grupo(IdGrupo = id, Nome = nome, Descricao = descricao, DataCriacao = dt)
                val response = ApiService.grupoInstance.atualizarGrupo(id, grupoAtualizado)
                if (response.isSuccessful) {
                    _grupos.value = _grupos.value.map {
                        if (it.IdGrupo == id) response.body() ?: it else it
                    }
                    _mensagem.value = "Grupo $id atualizado com sucesso."
                } else {
                    _mensagem.value = "Erro ao atualizar grupo: ${response.code()}"
                }
            } catch (e: Exception) {
                _mensagem.value = "Erro ao atualizar grupo: ${e.message}"
            }
        }
    }

    //HANDLE MEMBRO
    fun adicionarMembro(idMembro: Int, idUsuario: Int, idGrupo: Int, dt: LocalDateTime) {
        coroutineScope.launch {
            try {
                val novoMembro = Membro(IdMembro = idMembro, IdUsuario = idUsuario, Idgrupo = idGrupo, DataDeEntrada = dt)
                val membroAdicionado = ApiService.membroInstance.adicionarMembro(novoMembro)
                _mensagem.value = "Membro adicionado com sucesso ao grupo $idGrupo."
            } catch (e: Exception) {
                _mensagem.value = "Erro ao adicionar membro: ${e.message}"
            }
        }
    }

    fun removerMembro(idMembro: Int, idUsuario: Int, idGrupo: Int, dt: LocalDateTime) {
        coroutineScope.launch {
            try {
                val membroRemover = Membro(IdMembro = idMembro, IdUsuario = idUsuario, Idgrupo = idGrupo, DataDeEntrada = dt)
                val response = ApiService.membroInstance.removerMembro(membroRemover)
                if (response.isSuccessful) {
                    _mensagem.value = "Membro removido do grupo $idGrupo com sucesso."
                } else {
                    _mensagem.value = "Erro ao remover membro: ${response.code()}"
                }
            } catch (e: Exception) {
                _mensagem.value = "Erro ao remover membro: ${e.message}"
            }
        }
    }
}