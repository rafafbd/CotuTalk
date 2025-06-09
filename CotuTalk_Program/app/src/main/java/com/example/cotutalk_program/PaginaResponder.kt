package com.example.cotutalk_program.ui.theme

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cotutalk_program.AcessoAPI.data.Resposta
import com.example.cotutalk_program.AcessoAPI.viewmodel.GrupoViewModel
import com.example.cotutalk_program.AcessoAPI.viewmodel.PostagemViewModel
import com.example.cotutalk_program.AcessoAPI.viewmodel.UserPreferences
import com.example.cotutalk_program.AcessoAPI.viewmodel.UsuarioViewModel
import com.example.cotutalk_program.BottomNavigationBar
import com.example.cotutalk_program.Post
import com.example.cotutalk_program.PostUI
import org.threeten.bp.LocalDateTime
import android.util.Log
import androidx.navigation.NavController

class PaginaResponder : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CotuTalk_ProgramTheme {

            }
        }
    }
}

fun postarResposta(resposta: String){
    //
    //
    //
}


/*@Composable
fun responder(navController: NavHostController , IdPostagem : Int) {
    val postagemViewModel = PostagemViewModel()
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

    postagemViewModel.buscarPostagem(IdPostagem)
    postagemViewModel.buscarRespostasPorPostagem(IdPostagem)

    val oPost = postagemViewModel.postagensDetalhe
    val outraRespostas = postagemViewModel.respostasPostagem

    var resposta by remember { mutableStateOf("") }

    Scaffold (
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        Column(
            Modifier
                .background(roxo80)
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            PostUI(
//                Post(
//                    100,
//                    "Vini.guep",
//                    "defaultprofile",
//                    "",
//                    "Como eu faço herança em C#???",
//                    "Informatica"
//                )
//            )

            Spacer(Modifier.height(25.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 10.dp)) {

                TextField(
                    value = resposta,
                    onValueChange = { resposta = it },
                    singleLine = false,
                    minLines = 20,
                    placeholder = {
                        Text("Escreva sua resposta aqui...", color = branco)
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = roxo50,
                        unfocusedContainerColor = roxo50,
                        focusedTextColor = branco,
                        unfocusedTextColor = branco
                    ),
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth(0.9f)
                )

                Spacer(Modifier.height(25.dp))

                Box (Modifier.fillMaxWidth(0.9f)){
                    BotaoEstilizado("Responder") { postagemViewModel.adicionarResposta( Resposta(IdResposta= 0, IdPostagem = IdPostagem,IdUsuario = 0, Conteudo = resposta, DataComentario = LocalDateTime.now()))}
                }
            }
        }
    }

}*/
@Composable
fun responder(navController: NavController, IdPostagem: Int) {
    val grupoViewModel = remember { GrupoViewModel() }
    val postagemViewModel = remember { PostagemViewModel() }
    val usuarioViewModel = remember { UsuarioViewModel() } // Supondo que você tenha esse ViewModel
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

    val usuarioId = sharedPref.getInt("Id", 0)

    postagemViewModel.buscarPostagem(IdPostagem)
    postagemViewModel.buscarRespostasPorPostagem(IdPostagem)

    val oPost by postagemViewModel.postagemDetalhe.collectAsState()

    oPost?.let { grupoViewModel.buscarGrupo(it.IdGrupo) }
    oPost?.let { usuarioViewModel.buscarUsuario(it.IdUsuario) }

    val respostas by postagemViewModel.respostasPostagem.collectAsState()
    val usuario by usuarioViewModel.usuarioDetalhe.collectAsState()
    val grupo by grupoViewModel.grupoDetalhe.collectAsState()

    var resposta by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(roxo80)
                .padding(innerPadding)
        ) {
            // Postagem no topo


            PostUI(
                Post(
                    Id = oPost?.IdPostagem ?: 1,
                    nome = usuario?.nome ?: "Nome Desconhecido",
                    foto = usuario?.imagePath ?: "",
                    grupo = grupo?.Nome ?: "Grupo Desconhecido",
                    message = oPost?.conteudo ?: "Conteúdo Vazio",
                    dataHorario = oPost?.dataCriacao?.toString() ?: "Data Desconhecida"
                ),
                navController = navController
            )


            Spacer(Modifier.height(10.dp))

            respostas?.let { listaDeRespostas ->
                if (listaDeRespostas.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 10.dp)
                    ) {
                        items(listaDeRespostas) { respostaItem ->
                            RespostaUI(resposta = respostaItem, usuarioViewModel = usuarioViewModel)
                        }
                    }
                } else {
                    Text("Nenhuma resposta ainda.")
                }
            }


            // Campo de resposta
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(10.dp)
            ) {
                TextField(
                    value = resposta,
                    onValueChange = { resposta = it },
                    singleLine = false,
                    minLines = 4, // reduzido de 20 para algo mais adequado
                    placeholder = {
                        Text("Escreva sua resposta aqui...", color = branco)
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = roxo50,
                        unfocusedContainerColor = roxo50,
                        focusedTextColor = branco,
                        unfocusedTextColor = branco
                    ),
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth(0.95f)
                )

                Spacer(Modifier.height(10.dp))

                Box(modifier = Modifier.fillMaxWidth(0.95f)) {
                    BotaoEstilizado("Responder") {
                        postagemViewModel.adicionarResposta(
                            Resposta(
                                IdResposta = 0,
                                IdPostagem = IdPostagem,
                                IdUsuario = usuarioId,
                                Conteudo = resposta,
                                DataComentario = LocalDateTime.now()
                            )
                        )
                        resposta = ""
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}
