package com.example.cotutalk_program.AcessoAPI.viewmodel

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigator
import com.example.cotutalk_program.AcessoAPI.data.Curtida
import com.example.cotutalk_program.AcessoAPI.data.EmailRequest
import com.example.cotutalk_program.AcessoAPI.data.LoginRequest
import com.example.cotutalk_program.AcessoAPI.data.Postagem
import com.example.cotutalk_program.AcessoAPI.data.Resposta
import com.example.cotutalk_program.AcessoAPI.data.Usuario
import com.example.cotutalk_program.AcessoAPI.data.ValidarCodigoRequest
import com.example.cotutalk_program.AcessoAPI.network.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
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

    private val _postsUsuario = MutableStateFlow<List<Postagem>>(emptyList())
    val postsUsuario: StateFlow<List<Postagem?>> = _postsUsuario




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

    fun adicionarUsuario(nome: String, email: String, senha: String, biografia : String, imagePath: String? = null, navController: NavController, context: Context) {
        coroutineScope.launch {
            try {
                val path = imagePath ?: "perfil"
                val novoUsuario = Usuario(idUsuario = 0, nome = nome, email = email, senha = senha, biografia = biografia, imagePath = path)
                val usuarioCriado = ApiService.usuarioInstance.adicionarUsuario(novoUsuario)
                _usuarios.value = _usuarios.value + usuarioCriado
                _mensagem.value = "Usuário criado com ID ${usuarioCriado.idUsuario}"
                fazerLogin(email, senha, navController, context)
            } catch (e: Exception) {
                _mensagem.value = "Erro ao criar usuário: ${e.message}"
            }
        }
    }

    fun atualizarUsuario(id: Int, nome: String, email: String, senha: String) {
        coroutineScope.launch {
            try {
                val usuarioAtualizado = Usuario(idUsuario = id, nome = nome, email = email, senha = senha, biografia = "", imagePath="")
                val response = ApiService.usuarioInstance.atualizarUsuario(id, usuarioAtualizado)
                if (response.isSuccessful) {
                    _usuarios.value = _usuarios.value.map {
                        if (it.idUsuario == id) usuarioAtualizado else it
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

    fun atualizarUsuarioPorEmail(
        email: String,
        id: Int = -1,
        nome: String = "",
        novaSenha: String = "",
        novaBiografia: String = "",
        novaImagePath: String = "",
        navController: NavController
    ) {
        coroutineScope.launch {
            try {
                // Buscar usuário atual para manter dados que não devem ser alterados
                val usuarioAtual = _usuarios.value.find { it.email == email }
                if (usuarioAtual == null) {
                    _mensagem.value = "Usuário com e-mail $email não encontrado."
                    return@launch
                }

                val usuarioAtualizado = Usuario(
                    idUsuario = if (id != -1) id else usuarioAtual.idUsuario,
                    nome = if (nome.isNotBlank()) nome else usuarioAtual.nome,
                    email = email,
                    senha = if (novaSenha.isNotBlank()) novaSenha else usuarioAtual.senha,
                    biografia = if (novaBiografia.isNotBlank()) novaBiografia else usuarioAtual.biografia,
                    imagePath = if (novaImagePath.isNotBlank()) novaImagePath else usuarioAtual.imagePath
                )

                val response = ApiService.usuarioInstance.atualizarUsuarioPorEmail(email, usuarioAtualizado)
                if (response.isSuccessful) {
                    _usuarios.value = _usuarios.value.map {
                        if (it.email == email) usuarioAtualizado else it
                    }
                    _mensagem.value = "Usuário $email atualizado com sucesso."
                    _usuarioDetalhe.value = usuarioAtualizado
                    navController.navigate("Principal")
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
                    _usuarios.value = _usuarios.value.filter { it.idUsuario != id }
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
    fun fazerLogin(username: String, senha: String, navController: NavController, context: Context){
        coroutineScope.launch {
            try {
                val loginReq = LoginRequest(username, senha)
                val response = ApiService.usuarioInstance.login(loginReq)
                if (response.isSuccessful) {
                    val usuarioLogado = response.body()
                    if (usuarioLogado != null) {
                        // salvar
                        val sharedPref = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                        with(sharedPref.edit()) {
                            putInt("Id", usuarioLogado.idUsuario)
                            putString("Nome", usuarioLogado.nome)
                            putString("Email", usuarioLogado.email)
                            putString("Biografia", usuarioLogado.biografia)
                            putString("ImagePath", usuarioLogado.imagePath)
                            apply()
                        }
//                        val idPost: Int = 2
//                        navController.navigate("paginaGrupo/${idPost}")
                        navController.navigate("Principal")
                    } else {
                        _mensagem.value = "Erro ao obter dados do usuário"
                    }
                } else {
                    _mensagem.value = "Usuário ou senha inválidos"
                }
            } catch (e: Exception) {
                _mensagem.value = "Erro no login: ${e.message}"
            }
        }

    }

    fun enviarEmail(email : String, navController: NavController, where : String) {
        coroutineScope.launch {
            try {
                val emailRequest = EmailRequest(email)
                val response = ApiService.verificaoInstance.enviarEmail(emailRequest)
                if (response.isSuccessful){
                    navController.navigate("EmailRecuperacao/${where}")
                } else {
                    _mensagem.value = "Email não enviado"
                }
            } catch (e: Exception){
                _mensagem.value = "Erro ao enviar email: ${e.message}"
            }
        }
    }

    fun validarCodigo(email : String, codigo : String, navController: NavController, where : String) {
        coroutineScope.launch {
            try {
                val codeRequest = ValidarCodigoRequest(email, codigo)
                val response = ApiService.verificaoInstance.validarCodigo(codeRequest)
                if (response.isSuccessful){
                    navController.navigate(where)
                } else {
                    _mensagem.value = "*Codigo invalido"
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

    //IMAGEM
    //upload
    suspend fun uploadImage(context: Context, uri: Uri) {
        val contentResolver = context.contentResolver

        // Detectar o tipo MIME e extensão
        val mimeType = contentResolver.getType(uri) ?: "image/*"
        val extension = MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(mimeType) ?: "jpg"

        val inputStream = contentResolver.openInputStream(uri) ?: return
        val fileName = "upload_${System.currentTimeMillis()}.$extension"
        val file = File(context.cacheDir, fileName)

        // Copiar conteúdo para arquivo temporário
        file.outputStream().use { output ->
            inputStream.copyTo(output)
        }

        // Criar corpo da requisição
        val requestFile = file.asRequestBody(mimeType.toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("image", file.name, requestFile)

        try {
            val response = ApiService.usuarioInstance.uploadImage(imagePart)
            if (response.isSuccessful){
                _mensagem.value = fileName
            } else {
                _mensagem.value = "Falha no upload da imagem"
            }
        } catch (e: Exception) {
            _mensagem.value = "Falha no upload da imagem: $e"
        }
    }

    //pegar as postagens de um usuario
    fun buscarPostagensUsuario(id: Int) {
        coroutineScope.launch {
            try {
                val response = ApiService.usuarioInstance.buscarPostsUsuario(id)
                if (response.isSuccessful) {
                    _postsUsuario.value = response.body()!!
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