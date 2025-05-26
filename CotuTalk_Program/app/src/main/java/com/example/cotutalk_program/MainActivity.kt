package com.example.cotutalk_program

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cotutalk_program.ui.theme.CotuTalk_ProgramTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import com.example.cotutalk_program.PaginaCriarGrupo
import com.example.cotutalk_program.ui.theme.responder
import com.example.cotutalk_program.ui.theme.roxo80
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.cotutalk_program.AcessoAPI.data.Postagem
import com.example.cotutalk_program.AcessoAPI.data.PostagemUI
import com.example.cotutalk_program.AcessoAPI.viewmodel.PostagemViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CotuTalk_ProgramTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "Login") {
                    composable(route = "Login") {
                        paginaLogin(navController)
                    }
                    composable(route = "Registrar") {
                        paginaRegistrar(navController)
                    }
                    composable(route = "EsqueceuSenha") {
                        EsqueceuSenha(navController)
                    }
                    composable(
                        route = "EmailRecuperacao/{where}",
                        arguments = listOf(navArgument("where") { defaultValue = "" })
                    ) { backStackEntry ->
                        val where = backStackEntry.arguments?.getString("where") ?: ""
                        EmailRecuperacao(navController, where)
                    }
                    composable(route = "Config") {
                        paginaConfigurar(navController)
                    }
                    composable(route = "NovaSenha") {
                        NovaSenha(navController)
                    }
                    composable(route = "Principal"){ backStackEntry ->
                        val postagemViewModel = viewModel<PostagemViewModel>(backStackEntry)
                        TelaPrincipal(navController, postagemViewModel)
                    }
                    composable(
                        route = "responder/{idPost}",
                    ) { backStackEntry ->
                        val idPost = backStackEntry.arguments?.getInt("idPost")

                        val postagemViewModel = viewModel<PostagemViewModel>(backStackEntry)

                        TelaRespostas(navController = navController, postModel = postagemViewModel, idPost = idPost!!)
                    }
//                    composable(
//                        route = "Config/{loginRequest}",
//                        arguments = listOf(navArgument("loginRequest") {type = NavType.StringType})
//                        ) {
//                        val loginRequest = it.arguments?.getString("loginRequest") ?: ""
//                        paginaConfigurar(navController, loginRequest)
//                    }
                    composable(route = "Pesquisa") {
                        Search(navController)
                    }
                    composable(route = "Resposta") {
                        responder(navController)
                    }
                    composable(route = "Verificar") {
                        paginaVerificar(navController)
                    }
                    composable(route = "criarGrupo") {
                        PaginaCriarGrupo(navController)
                    }


                }
            }
        }
    }
}


@Composable
fun PostUI(post: Post) {
    val context = LocalContext.current
    val resourceId = context.resources.getIdentifier(post.foto, "drawable", context.packageName)

    val imagePainter = if (resourceId != 0) {
        painterResource(id = resourceId)
    } else {
        painterResource(id = R.drawable.defaultprofile)
    }



    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Primeira linha: foto e nome
        Row(modifier = Modifier.padding(bottom = 8.dp)) {
            Image (
                painter = imagePainter,
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
            )
            Text(
                text = "@" + post.nome,
                color = Color.White,
                fontSize = 20.sp,  // Aumentando o tamanho da fonte
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp)
            )
        }

        // Segunda linha: mensagem do post
        Text(
            text = post.message,
            color = Color.White,
            fontSize = 17.sp,  // Aumentando o tamanho da fonte
            modifier = Modifier.padding(start = 8.dp)
        )

        Row(modifier = Modifier.padding(start = 8.dp, top = 8.dp)) {
            Text(post.curtidas.toString(), color = Color.LightGray)
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Curtidas",
                tint = Color.LightGray,
                modifier = Modifier
                    .size(18.dp)
                    .padding(start = 2.dp)
            )
            Spacer(modifier = Modifier.width(40.dp))
            Text(post.comentarios.size.toString(), color = Color.LightGray)
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Comentários",
                tint = Color.LightGray,
                modifier = Modifier
                    .size(18.dp)
                    .padding(start = 2.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
        )
    }
}

@Composable
fun principal(navController: NavController, postModel: PostagemViewModel) {
    postModel.listarPostagens()

    Scaffold(
        modifier = Modifier.background(roxo80),
        bottomBar = {
            //BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(roxo80)
                .padding(innerPadding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo2),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(620.dp)
                    .padding(16.dp)  // Adicionando padding à imagem
            )
            Post(postModel.postagens)
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
                    navController.navigate("criarGrupo")
                },
                containerColor = Color(0xFF6C40BF), // roxo80 ou a cor que preferir
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
        floatingActionButtonPosition = androidx.compose.material3.FabPosition.Center,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(roxo80)
                .padding(innerPadding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo2),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(620.dp)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun TelaPrincipal(navController: NavHostController, postModel: PostagemViewModel) {
    val post1 = Post(
        "viniguep",
        "Vini",
        "{LocalDateTime.now()}",
        "Rpzd o que fazer com a minha vida?"
    )
    val post2 = Post(
        "gui.OProfeta",
        "profeta",
        "{LocalDateTime.now()}",
        "Estou enfrentando dificuldades para entender uma matéria de matemática, alguém pode me ajudar?"
    )
    val post3 = Post(
        "rafa.Vasco",
        "vasco",
        "{LocalDateTime.now()}",
        "Quais dicas vocês têm para melhorar meu caminhão de bombeiro de papelão?"
    )
    for (i in 0 until 43) {
        post2.adicionarCurtida()
        post2.adicionarCurtida()
        post1.removerCurtida()
    }

    Scaffold(
        modifier = Modifier.background(roxo80),
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(roxo80)
                .padding(innerPadding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo2),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(620.dp)
                    .padding(16.dp)  // Adicionando padding à imagem
            )
            PostUI(post = post1)
            PostUI(post = post2)
            PostUI(post = post3)
        }
    }
    Scaffold(
        modifier = Modifier.background(roxo80),
        bottomBar = {
            //BottomNavigationBar()
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("criarGrupo")
                },
                containerColor = Color(0xFF6C40BF), // roxo80 ou a cor que preferir
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
        floatingActionButtonPosition = androidx.compose.material3.FabPosition.Center,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(roxo80)
                .padding(innerPadding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo2),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(620.dp)
                    .padding(16.dp)
            )
            PostUI(post = post1)
            PostUI(post = post2)
            PostUI(post = post3)
        }
    }

}


@Composable
fun BottomNavigationBar(navController: NavController) {
    Column {
        // Borda superior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
        )

        // BottomAppBar com a barra de navegação
        BottomAppBar(
            modifier = Modifier.height(62.dp),
            containerColor = roxo80,
            contentColor = Color.White,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = {navController.navigate("Principal")}) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_home),
                        contentDescription = "Home",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
                IconButton(onClick = {navController.navigate("Pesquisa")}) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_search),
                        contentDescription = "Search",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
                IconButton(onClick = {navController.navigate("Notificacao")}) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_notification),
                        contentDescription = "Notifications",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
                IconButton(onClick = {navController.navigate("Usuario")}) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_profile),
                        contentDescription = "Profile",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    }
}

