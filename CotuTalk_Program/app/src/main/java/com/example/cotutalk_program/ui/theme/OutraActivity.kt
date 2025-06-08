package com.example.cotutalk_program.ui.theme

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.cotutalk_program.AcessoAPI.data.Postagem
import com.example.cotutalk_program.AcessoAPI.data.PostagemUI
import com.example.cotutalk_program.AcessoAPI.data.Usuario
import com.example.cotutalk_program.AcessoAPI.network.ApiService
import com.example.cotutalk_program.AcessoAPI.viewmodel.PostagemViewModel
import com.example.cotutalk_program.AcessoAPI.viewmodel.UsuarioViewModel
import com.example.cotutalk_program.R
import com.example.cotutalk_program.ui.theme.CotuTalk_ProgramTheme
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CotuTalk_ProgramTheme {

            }
        }
    }
}

@Composable
fun PostComApi(navController: NavController, post: Postagem, qtsCurtidas: Int){
    val context = LocalContext.current
    val postagemViewModel = PostagemViewModel()


    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Primeira linha: foto e nome
        Row(modifier = Modifier
            .padding(bottom = 8.dp)
            .clickable { navController.navigate("") }) {
//            AsyncImage(
//                model = "${ApiService.BASE_URL}img/${post.Usuario.imagePath}",
//                contentDescription = "foto do perfil",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .size(64.dp)
//                    .clip(CircleShape)
//            )
//            Text(
//                text = "@${post.Usuario.nome}",
//                color = Color.White,
//                fontSize = 20.sp,
//                modifier = Modifier
//                    .align(Alignment.CenterVertically)
//                    .padding(start = 8.dp)
//            )
        }

//        Text(
//            text = post.Grupo.Nome,
//            color = Color.Gray,
//            fontSize = 12.sp,
//            modifier = Modifier.clickable {
//                navController.navigate("") // tela do grupo, passar o id dele
//            }
//        )

        // Segunda linha: mensagem do post
        Text(
            text = post.Conteudo,
            color = Color.White,
            fontSize = 17.sp,  // Aumentando o tamanho da fonte
            modifier = Modifier.padding(start = 8.dp)
        )

        Row(modifier = Modifier.padding(start = 8.dp, top = 8.dp)) {
            Text(qtsCurtidas.toString(), color = Color.LightGray)
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Curtidas",
                tint = Color.LightGray,
                modifier = Modifier
                    .size(18.dp)
                    .padding(start = 2.dp)
            )
            Spacer(modifier = Modifier.width(40.dp))
            Text(qtsCurtidas.toString(), color = Color.LightGray)
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
fun BotaoEstilizado(texto: String, click: () -> Unit){
    Button(
        onClick = click,
        colors = ButtonDefaults.buttonColors(
            containerColor = lilas20,
            contentColor = branco
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ){
        Text(text = texto, fontSize = 22.sp)
    }

//    @Composable
//    fun AppDrawer(
//        drawerState: DrawerState,
//        navController: NavHostController,
//        currentRoute: String?
//    ) {
//        val scope = rememberCoroutineScope()
//
//        ModalDrawerSheet(
//            drawerContainerColor = roxo80,
//            drawerContentColor = Color.White,
//            modifier = Modifier.fillMaxHeight()
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 24.dp)
//            ) {
//                DrawerItem(
//                    iconId = R.drawable.icon_home,
//                    label = "Início",
//                    destination = "Principal",
//                    navController = navController,
//                    currentRoute = currentRoute,
//                    closeDrawer = { scope.launch { drawerState.close() } }
//                )
//                DrawerItem(
//                    iconId = R.drawable.icon_search,
//                    label = "Pesquisa",
//                    destination = "Pesquisa",
//                    navController = navController,
//                    currentRoute = currentRoute,
//                    closeDrawer = { scope.launch { drawerState.close() } }
//                )
//                DrawerItem(
//                    iconId = R.drawable.icon_notification,
//                    label = "Notificações",
//                    destination = "Notificacao",
//                    navController = navController,
//                    currentRoute = currentRoute,
//                    closeDrawer = { scope.launch { drawerState.close() } }
//                )
//                DrawerItem(
//                    iconId = R.drawable.icon_profile,
//                    label = "Usuário",
//                    destination = "Usuario",
//                    navController = navController,
//                    currentRoute = currentRoute,
//                    closeDrawer = { scope.launch { drawerState.close() } }
//                )
//            }
//        }
//    }
//
//    @Composable
//    fun DrawerItem(
//        iconId: Int,
//        label: String,
//        destination: String,
//        navController: NavHostController,
//        currentRoute: String?,
//        closeDrawer: () -> Unit
//    ) {
//        val isSelected = currentRoute == destination
//
//        val backgroundColor = if (isSelected) roxo40 else Color.Transparent
//
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier
//                .fillMaxWidth()
//                .clickable {
//                    if (!isSelected) {
//                        navController.navigate(destination) {
//                            launchSingleTop = true
//                            restoreState = true
//                        }
//                    }
//                    closeDrawer()
//                }
//                .background(backgroundColor)
//                .padding(horizontal = 16.dp, vertical = 12.dp)
//        ) {
//            Icon(
//                painter = painterResource(id = iconId),
//                contentDescription = label,
//                tint = Color.White,
//                modifier = Modifier.size(24.dp)
//            )
//            Spacer(modifier = Modifier.width(16.dp))
//            Text(text = label, fontSize = 16.sp, color = Color.White)
//        }
//    }
//
}
