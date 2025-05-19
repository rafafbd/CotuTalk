package com.example.cotutalk_program.AcessoAPI.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.cotutalk_program.AcessoAPI.data.Curtida
import com.example.cotutalk_program.AcessoAPI.data.EmailRequest
import com.example.cotutalk_program.AcessoAPI.data.LoginRequest
import com.example.cotutalk_program.AcessoAPI.data.Resposta
import com.example.cotutalk_program.AcessoAPI.data.Usuario
import com.example.cotutalk_program.AcessoAPI.data.ValidarCodigoRequest
import com.example.cotutalk_program.AcessoAPI.network.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.URLEncoder

class UsuarioViewModel : ViewModel() {
    private val _usuarios = MutableStateFlow<List<Usuario>>(emptyList())
    val usuarios: StateFlow<List<Usuario>> = _usuarios

    private val _usuarioDetalhe = MutableStateFlow<Usuario?>(null)
    val usuarioDetalhe: StateFlow<Usuario?> = _usuarioDetalhe

    private val _mensagem = MutableStateFlow("")
    val mensagem: StateFlow<String> = _mensagem
    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    private val _curtidasUsuario = MutableStateFlow<List<Curtida>>(emptyList())
    val curtidasUsuario: StateFlow<List<Curtida>> = _curtidasUsuario

    private val _respostasUsuario = MutableStateFlow<List<Resposta>>(emptyList())
    val respostasUsuario: StateFlow<List<Resposta>> = _respostasUsuario

    private val _respostaAtualizada = MutableStateFlow<Resposta?>(null)
    val respostaAtualizada: StateFlow<Resposta?> = _respostaAtualizada



    // CRUD
    fun listarUsuarios() {
        coroutineScope.launch {
            try {
                _usuarios.value = ApiService.usuarioInstance.listarUsuarios()
                _mensagem.value = "Usuários carregados com sucesso."
            } catch (e: Exception) {
                _mensagem.value = "Erro ao carregar usuários: ${e.message}"
            }
        }
    }

    fun buscarUsuario(id: Int) {
        coroutineScope.launch {
            try {
                _usuarioDetalhe.value = ApiService.usuarioInstance.buscarUsuario(id)
                _mensagem.value =
                    if (_usuarioDetalhe.value != null) "Usuário encontrado." else "Usuário não encontrado."
            } catch (e: Exception) {
                _mensagem.value = "Erro ao buscar usuário: ${e.message}"
            }
        }
    }

    fun adicionarUsuario(nome: String, email: String, senha: String) {
        coroutineScope.launch {
            try {
                val novoUsuario = Usuario(IdUsuario = 0, Nome = nome, Email = email, Senha = senha)
                val usuarioCriado = ApiService.usuarioInstance.adicionarUsuario(novoUsuario)
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
                val response = ApiService.usuarioInstance.atualizarUsuario(id, usuarioAtualizado)
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
                val response = ApiService.usuarioInstance.deletarUsuario(id)
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

    //LOGIN E VERIFICACAO
    fun fazerLogin(username: String, senha: String, navController: NavController){
        coroutineScope.launch {
            try {
                val loginReq = LoginRequest(username, senha)
                val response = ApiService.usuarioInstance.login(loginReq)
                if (response.isSuccessful){
                    // alguma linha de codigo para guardar no env os dados do usuario
                    var usuarioLogaldo = response.body() // supoem-se que o body da response tem o Usuario
                    navController.navigate("Principal")
                }
                else{
                    _mensagem.value = "Usuário ou senha inválidos"
                }
            }
            catch (e: Exception){
                _mensagem.value = "Erro no login: ${e.message}"
            }
        }
    }

    fun enviarEmail(email : String, navController: NavController) {
        coroutineScope.launch {
            try {
                val emailRequest = EmailRequest(email)
                val response = ApiService.verificaoInstance.enviarEmail(emailRequest)
                if (response.isSuccessful){
                    val encodedEmail = URLEncoder.encode(email, "UTF-8")
                    navController.navigate("EmailRecuperacao/$encodedEmail")
                } else {
                    _mensagem.value = "Email não enviado"
                }
            } catch (e: Exception){
                _mensagem.value = "Erro ao enviar email: ${e.message}"
            }
        }
    }

    fun validarCodigo(email : String, codigo : String, navController: NavController) {
        coroutineScope.launch {
            try {
                val codeRequest = ValidarCodigoRequest(email, codigo)
                val response = ApiService.verificaoInstance.validarCodigo(codeRequest)
                if (response.isSuccessful){
                    navController.navigate("Config")
                } else {
                    _mensagem.value = "Codigo invalido!"
                }
            } catch (e : Exception) {
                _mensagem.value = "Erro ao validar codigo: ${e.message}"
            }
        }
    }

    //HANDLER CURTIDA
    fun buscarCurtidasPorUsuario(idUsuario: Int) {
        coroutineScope.launch {
            try {
                val curtidas = ApiService.curtidaInstance.buscarCurtidasPorUsuario(idUsuario)
                _mensagem.value = if (curtidas.isNotEmpty()) {
                    "Curtidas encontradas para o usuário $idUsuario."
                } else {
                    "Nenhuma curtida encontrada para o usuário $idUsuario."
                }
                _curtidasUsuario.value = curtidas
            } catch (e: Exception) {
                _mensagem.value = "Erro ao buscar curtidas do usuário: ${e.message}"
            }
        }
    }

    fun deletarCurtidaPorUsuario(idUsuario: Int) {
        coroutineScope.launch {
            try {
                val response = ApiService.curtidaInstance.deletarCurtidaPorUsuario(idUsuario)
                if (response.isSuccessful) {
                    _mensagem.value = "Curtida do usuário $idUsuario removida com sucesso."
                    _curtidasUsuario.value = _curtidasUsuario.value.filter { it.IdUsuario != idUsuario }
                } else {
                    _mensagem.value = "Erro ao deletar curtida: código ${response.code()}"
                }
            } catch (e: Exception) {
                _mensagem.value = "Erro ao deletar curtida do usuário: ${e.message}"
            }
        }
    }

    //HANDLE RESPOSTA
    fun buscarRespostasPorUsuario(idUsuario: Int) {
        coroutineScope.launch {
            try {
                val response = ApiService.respostaInstance.buscarRespostasPorUsuario(idUsuario)
                if (response.isSuccessful) {
                    _respostasUsuario.value = response.body() ?: emptyList()
                } else {
                    _mensagem.value = "Erro ao buscar respostas do usuário"
                }
            } catch (e: Exception) {
                _mensagem.value = "Exceção: ${e.message}"
            }
        }
    }

    fun atualizarResposta(id: Int, novaResposta: Resposta) {
        coroutineScope.launch {
            try {
                val response = ApiService.respostaInstance.atualizarResposta(id, novaResposta)
                if (response.isSuccessful) {
                    _respostaAtualizada.value = response.body()
                    _mensagem.value = "Resposta atualizada com sucesso"
                } else {
                    _mensagem.value = "Erro ao atualizar resposta"
                }
            } catch (e: Exception) {
                _mensagem.value = "Exceção: ${e.message}"
            }
        }
    }

    fun deletarResposta(id: Int) {
        coroutineScope.launch {
            try {
                val response = ApiService.respostaInstance.deletarResposta(id)
                if (response.isSuccessful) {
                    _mensagem.value = "Resposta deletada com sucesso"
                } else {
                    _mensagem.value = "Erro ao deletar resposta"
                }
            } catch (e: Exception) {
                _mensagem.value = "Exceção: ${e.message}"
            }
        }
    }
}