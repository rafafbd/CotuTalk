package com.example.cotutalk_program

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cotutalk_program.AcessoAPI.data.Grupo
import com.example.cotutalk_program.AcessoAPI.viewmodel.GrupoViewModel
import com.example.cotutalk_program.ui.theme.branco
import com.example.cotutalk_program.ui.theme.roxo70
import com.example.cotutalk_program.ui.theme.roxo80
import com.example.cotutalk_program.R
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect

@Composable
fun Search(navController: NavHostController) {
    var pesquisa by remember { mutableStateOf("") }

    val grupoModel = remember { GrupoViewModel() }
    val osGrupos by grupoModel.grupos.collectAsState() // coleta os dados do StateFlow

    // Buscar os grupos da API
    LaunchedEffect(Unit) {
        grupoModel.listarGrupos()
    }

    // Filtrar os grupos com base no texto digitado
    val gruposFiltrados = osGrupos.filter {
        it.Nome.contains(pesquisa, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(roxo80),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Campo de pesquisa
        TextField(
            value = pesquisa,
            onValueChange = { pesquisa = it },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = roxo70,
                unfocusedContainerColor = roxo70,
                focusedTextColor = branco,
                unfocusedTextColor = branco
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.icon_search),
                    contentDescription = "Ícone de busca",
                    tint = Color.White
                )
            },
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .border(BorderStroke(0.dp, Color.Black), shape = RectangleShape)
                .shadow(4.dp, RectangleShape)
                .fillMaxWidth(0.9f)
                .padding(top = 20.dp)
                .height(50.dp)
        )

        // Lista de grupos filtrados
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            gruposFiltrados.forEach { grupo ->
                Text(
                    text = grupo.Nome,
                    color = Color.White,
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .background(roxo70, shape = RoundedCornerShape(8.dp))
                        .padding(12.dp)
                        .fillMaxWidth()
                )
            }
        }

        // Logo
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .alpha(0.5f)
                .fillMaxSize(0.5f)
        )

        // Barra de navegação
        BottomNavigationBar(navController)
    }
}
