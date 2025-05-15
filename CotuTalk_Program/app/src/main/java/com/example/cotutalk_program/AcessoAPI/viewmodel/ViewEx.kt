package com.example.clienteapp.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.clienteapp.data.Cliente
import com.example.clienteapp.network.RetrofitClient
import com.example.cotutalk_program.AcessoAPI.data.Usuario
import com.example.cotutalk_program.AcessoAPI.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ClienteViewModel : ViewModel() {
    private val _clientes = mutableStateOf<List<Usuario>>(emptyList())
    val clientes: State<List<Usuario>> = _clientes

    private val _clienteDetalhe = mutableStateOf<Usuario?>(null)
    val clienteDetalhe: State<Usuario?> = _clienteDetalhe

    private val _mensagem = mutableStateOf("")
    val mensagem: State<String> = _mensagem
    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)


    fun listarClientes() {
        coroutineScope.launch {
            try {
                _clientes.value = RetrofitClient.RetrofitClient.usuarioInstance.listarUsuarios()
                _mensagem.value = "Clientes carregados com sucesso."
            } catch (e: Exception) {
                _mensagem.value = "Erro ao carregar clientes: ${e.message}"
            }
        }
    }

    fun buscarCliente(id: Int) {
        coroutineScope.launch {
            try {
                _clienteDetalhe.value = RetrofitClient.instance.buscarCliente(id)
                _mensagem.value =
                    if (_clienteDetalhe.value != null) "Cliente encontrado." else "Cliente não encontrado."
            } catch (e: Exception) {
                _mensagem.value = "Erro ao buscar cliente: ${e.message}"
            }
        }
    }

    fun criarCliente(nome: String) {
        coroutineScope.launch {
            try {
                val novoCliente = Cliente(id = 0, nome = nome) // O ID será gerado pelo servidor
                val clienteCriado = RetrofitClient.instance.criarCliente(novoCliente)
                _clientes.value = _clientes.value + clienteCriado
                _mensagem.value = "Cliente criado com ID ${clienteCriado.id}"
            } catch (e: Exception) {
                _mensagem.value = "Erro ao criar cliente: ${e.message}"
            }
        }
    }

    fun atualizarCliente(id: Int, novoNome: String) {
        coroutineScope.launch {
            try {
                val clienteAtualizado = Cliente(id = id, nome = novoNome)
                val response = RetrofitClient.instance.atualizarCliente(id, clienteAtualizado)
                if (response.isSuccessful) {
                    _clientes.value = _clientes.value.map {
                        if (it.id == id) it.copy(nome = novoNome) else it
                    }
                    _mensagem.value = "Cliente $id atualizado."
                    _clienteDetalhe.value = clienteAtualizado
                } else {
                    _mensagem.value = "Erro ao atualizar cliente: ${response.code()}"
                }
            } catch (e: Exception) {
                _mensagem.value = "Erro ao atualizar cliente: ${e.message}"
            }
        }
    }

    fun deletarCliente(id: Int) {
        coroutineScope.launch {
            try {
                val response = RetrofitClient.instance.deletarCliente(id)
                if (response.isSuccessful) {
                    _clientes.value = _clientes.value.filter { it.id != id }
                    _mensagem.value = "Cliente $id deletado."
                    _clienteDetalhe.value = null
                } else {
                    _mensagem.value = "Erro ao deletar cliente: ${response.code()}"
                }
            } catch (e: Exception) {
                _mensagem.value = "Erro ao deletar cliente: ${e.message}"
            }
        }
    }

    // Expor mensagem de erro para a UI
    var mensagemerro = _mensagem
}
