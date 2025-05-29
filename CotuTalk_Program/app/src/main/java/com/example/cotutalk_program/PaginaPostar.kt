package com.example.cotutalk_program

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cotutalk_program.AcessoAPI.viewmodel.PostagemViewModel
import com.example.cotutalk_program.AcessoAPI.viewmodel.UserPreferences
import com.example.cotutalk_program.AcessoAPI.viewmodel.UsuarioViewModel
import com.example.cotutalk_program.ui.theme.BotaoEstilizado
import com.example.cotutalk_program.ui.theme.branco
import com.example.cotutalk_program.ui.theme.postarResposta
import com.example.cotutalk_program.ui.theme.roxo50
import com.example.cotutalk_program.ui.theme.roxo80

@Composable
fun postar(navController: NavHostController , Idgrupo : Int) {
    val postagemViewModel = PostagemViewModel()
    val usuarioviewModel = UsuarioViewModel()
    val contex = LocalContext.current
    val userIdFlow = UserPreferences.lerUsuario(contex)
    val userId by userIdFlow.collectAsState(initial = null)
    var postagem by remember { mutableStateOf("") }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 10.dp)) {

                TextField(
                    value = postagem,
                    onValueChange = { postagem = it },
                    singleLine = false,
                    minLines = 20,
                    placeholder = {
                        Text("Escreva sua postagem aqui...", color = branco)
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
                    BotaoEstilizado("Postar") { postagemViewModel.adicionarPostagem(idu = userId!! ,idg = Idgrupo , conteudo = postagem, idp = 0) }
                }
            }
        }
    }