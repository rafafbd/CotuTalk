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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import com.example.cotutalk_program.ui.theme.lilas20


@Composable
fun Search(navController: NavHostController) {
    var pesquisa by remember { mutableStateOf("") }

    val grupoModel = remember { GrupoViewModel() }
    val osGrupos by grupoModel.grupos.collectAsState()

    LaunchedEffect(Unit) {
        grupoModel.listarGrupos()
    }

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


        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp)
        ) {
            items(gruposFiltrados) { grupo ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = grupo.Nome,
                            color = Color.Black,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Button(
                                onClick = {
                                    println("Entrar no grupo ${grupo.Nome} (id=${grupo.IdGrupo})")
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = lilas20,
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Entrar")
                            }

                            Button(
                                onClick = {
                                    navController.navigate("paginaGrupo/${grupo.IdGrupo}")
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = lilas20,
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Detalhes")
                            }
                        }
                    }
                }
            }
        }


        // Logo
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .alpha(0.5f)
                .fillMaxSize(0.3f)
        )

        BottomNavigationBar(navController)
    }
}

