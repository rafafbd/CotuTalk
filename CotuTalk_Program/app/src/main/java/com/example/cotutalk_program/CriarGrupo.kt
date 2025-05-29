package com.example.cotutalk_program

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cotutalk_program.AcessoAPI.viewmodel.GrupoViewModel
import com.example.cotutalk_program.AcessoAPI.viewmodel.UserPreferences
import com.example.cotutalk_program.AcessoAPI.viewmodel.UsuarioViewModel
import com.example.cotutalk_program.ui.theme.BotaoEstilizado
import com.example.cotutalk_program.ui.theme.branco
import com.example.cotutalk_program.ui.theme.roxo50
import com.example.cotutalk_program.ui.theme.roxo70
import com.example.cotutalk_program.ui.theme.roxo80
import org.threeten.bp.LocalDateTime


@Composable
fun PaginaCriarGrupo(navController: NavHostController) {
    var nomeGrupo by remember { mutableStateOf("") }
    var descGrupo by remember { mutableStateOf("") }
    val userModel = UsuarioViewModel()
    val grupoModel = GrupoViewModel()

    val context = LocalContext.current
    val userIdFlow = UserPreferences.lerUsuario(context)
    val userId by userIdFlow.collectAsState(initial = null)

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

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo do app",
                modifier = Modifier.size(200.dp)
            )

            Text("Nome do grupo:",
                color = branco,
                fontSize = 16.sp)

            TextField(
                value = nomeGrupo,
                onValueChange = { nomeGrupo = it },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = roxo70,
                    unfocusedContainerColor = roxo70,
                    focusedTextColor = branco,
                    unfocusedTextColor = branco
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth(0.9f)
            )

            Spacer(Modifier.height(25.dp))

            Text("Descrição do grupo:",
                color = branco,
                fontSize = 16.sp
                )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 10.dp)) {

                TextField(
                    value = descGrupo,
                    onValueChange = { descGrupo = it },
                    singleLine = false,
                    minLines = 15,
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



                Spacer(Modifier.height(25.dp))

                Box (Modifier.fillMaxWidth(0.9f)){
                    BotaoEstilizado("Criar") {
                        grupoModel.adicionarGrupo(
                            nome = nomeGrupo,
                            descricao = descGrupo,
                            dt = LocalDateTime.now()
                        )
                    }
                }
            }
        }
    }
}