package com.example.cotutalk_program

import android.annotation.SuppressLint
import androidx.collection.intIntMapOf
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cotutalk_program.AcessoAPI.viewmodel.PostagemViewModel
import com.example.cotutalk_program.AcessoAPI.viewmodel.UserPreferences
import com.example.cotutalk_program.AcessoAPI.viewmodel.UsuarioViewModel
import com.example.cotutalk_program.ui.theme.roxo80


@Preview
@Composable
fun PaginaPerfilPreview(){
    val navController = rememberNavController()
    paginaPerfil(navController)
}

@Composable
fun paginaPerfil(navController: NavController) {
    val postagemViewModel = PostagemViewModel()
    val usuarioViewModel = UsuarioViewModel()
    val contex = LocalContext.current
    val userIdFlow = UserPreferences.lerUsuario(contex)
    val userId by userIdFlow.collectAsState(initial = null)
    if (userId != null)
    {
        usuarioViewModel.buscarUsuario(id = userId!!)
    }
    val usuario = usuarioViewModel.usuarioDetalhe.collectAsState()

    Scaffold(
        modifier = Modifier.background(roxo80),
        bottomBar = {
            BottomNavigationBar(navController)
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(roxo80)
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                // Perfil
                Row (verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.defaultprofile),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(usuario.value?.Nome ?: "", color = Color.White, fontWeight = FontWeight.Bold)
                        Text("Estudante de PD24", color = Color.Gray, fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text("27 Groups", color = Color.Gray)

                )

//                // Comentários simulados
//                Comentario(
//                    nome = "@rafael.vasco",
//                    mensagem = "IndentationError: expected an indented block\nEsse erro apareceu para mim na hora de criar um função em python",
//                    comentarios = 2,
//                    likes = 1000
//                )
//
//                Comentario(
//                    nome = "@gui.OProfeta",
//                    mensagem = "Geralmente esse erro aparece por causa de indentação",
//                    comentarios = 1,
//                    likes = 5200
//                )
            }
        }
    )
}


}