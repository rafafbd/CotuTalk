package com.example.cotutalk_program

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cotutalk_program.AcessoAPI.viewmodel.GrupoViewModel
import com.example.cotutalk_program.ui.theme.lilas10
import com.example.cotutalk_program.ui.theme.lilas20
import com.example.cotutalk_program.ui.theme.roxo80
import android.util.Log
import kotlinx.coroutines.launch

@Composable
fun PaginaGrupo(navController: NavController, idGrupo: Int){
    val contexto = LocalContext.current
    val grupoViewModel: GrupoViewModel = viewModel(contexto as androidx.lifecycle.ViewModelStoreOwner)


    LaunchedEffect(Unit) {
        grupoViewModel.buscarGrupo(idGrupo)
        grupoViewModel.buscarPostagensDoGrupo(idGrupo)
    }

    val grupo by grupoViewModel.grupoDetalhe.collectAsState()
    val postagens by grupoViewModel.postagensGrupo.collectAsState()

    val postagensFormatadas = postagens.mapNotNull { post ->
        try {
            Post(
                nome = post.Usuario.nome ?: "Usuário desconhecido",
                foto = post.Usuario.imagePath ?: "",
                dataHorario = post.dataCriacao.toString(),
                message = post.conteudo ?: "",
                grupo = post.Grupo.Nome ?: "Grupo desconhecido",
                Id = post.IdPostagem
            )
        } catch (e: Exception) {
            Log.e("PostMapping", "Erro ao mapear postagem: ${e.message}")
            null
        }
    }



    Scaffold(
    modifier = Modifier.background(roxo80),
    bottomBar = {
        BottomNavigationBar(navController)
    },
    floatingActionButton = {
        FloatingActionButton(
            onClick = {
                navController.navigate("paginaPostar/$idGrupo")
            },
            containerColor = lilas20,
            contentColor = Color.White,
            shape = CircleShape
        ) {
            Text(
                text = "+",
                fontSize = 30.sp,
                color = Color.White
            )
        }
    },
    floatingActionButtonPosition = FabPosition.End,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(roxo80)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Se o grupo ainda não foi carregado, exibe um indicador ou placeholder
            if (grupo == null) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(20.dp),
                    color = lilas20
                )
                Text("Carregando grupo...", color = Color.White)
            } else {
                // Se o grupo foi carregado, exibe os detalhes do grupo
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = grupo?.Nome ?: "Nome do Grupo",
                            color = Color.White,
                            fontSize = 32.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = grupo?.Descricao ?: "Nenhuma descrição disponível para este grupo.",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 16.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    color = Color.Gray.copy(alpha = 0.4f),
                    thickness = 1.dp
                )

                if (postagens.isEmpty()) {
                    Text(
                        "Nenhuma postagem ainda neste grupo.",
                        color = Color.White.copy(alpha = 0.6f),
                        modifier = Modifier.padding(top = 20.dp)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(roxo80)
                            .padding(innerPadding)
                    ) {
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                            }
                            postagensFormatadas.forEach { post ->
                                PostUI(post, navController)
                            }
                        }
                    }
                }
            }
        }
    }
}