package com.example.cotutalk_program

import android.annotation.SuppressLint
import android.content.Context
import androidx.collection.intIntMapOf
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.cotutalk_program.AcessoAPI.data.Usuario
import com.example.cotutalk_program.AcessoAPI.network.ApiService
import com.example.cotutalk_program.AcessoAPI.viewmodel.PostagemViewModel
import com.example.cotutalk_program.AcessoAPI.viewmodel.UserPreferences
import com.example.cotutalk_program.AcessoAPI.viewmodel.UsuarioViewModel
import com.example.cotutalk_program.ui.theme.roxo80
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
fun PaginaPerfilPreview(navController: NavController) {
    val context = LocalContext.current
    paginaPerfil(navController, context)
}

@Composable
fun paginaPerfil(navController: NavController, context: Context) {
    val sharedPref = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    val id = sharedPref.getInt("Id", 0)
    val nome = sharedPref.getString("Nome", "") ?: ""
    val biografia = sharedPref.getString("Biografia", "") ?: ""
    val imagePath = sharedPref.getString("ImagePath", "") ?: ""
    val viewModel = UsuarioViewModel()
    viewModel.buscarPostagensUsuario(id)
    val postagens by viewModel.postsUsuario.collectAsState()
    val postagensFormatadas = postagens.map { post ->
        Post(
            nome = nome,
            foto = imagePath,
            dataHorario = post?.dataCriacao.toString() ?: "Sem data",
            message = post?.conteudo ?: "",
            grupo = post?.Grupo!!.Nome
        )
    }


    LaunchedEffect(true) {

    }

    Scaffold(
        modifier = Modifier.background(roxo80),
        bottomBar = {
            BottomNavigationBar(navController)
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(roxo80)
                    .padding(paddingValues)
                    .padding(16.dp),
                contentPadding = PaddingValues(bottom = 80.dp) // espaço para não sobrepor a bottom bar
            ) {
                item {
                // Perfil
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = "${ApiService.BASE_URL}img/${imagePath}",
                        contentDescription = "foto do perfil",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Column {
                        Text(
                            nome,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            biografia,
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text("27 Groups", color = Color.Gray)
                Spacer(modifier = Modifier.height(16.dp))
                postagensFormatadas.forEach { post ->
                    PostUI(post)
                }
            }
            }
        }
    )
}