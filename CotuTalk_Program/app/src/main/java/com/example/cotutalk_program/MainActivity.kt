package com.example.cotutalk_program

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cotutalk_program.ui.theme.CotuTalk_ProgramTheme
import java.time.LocalDateTime
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CotuTalk_ProgramTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PreviewPost()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun PostUI(post: Post) {
    val context = LocalContext.current
    val resourceId = context.resources.getIdentifier(post.foto, "drawable", context.packageName)

    // Fallback to a default profile image if the dynamic resource is not found
    val imagePainter = if (resourceId != 0) {
        painterResource(id = resourceId)
    } else {
        painterResource(id = R.drawable.defaultprofile) // Use a default image if not found
    }

    Column(modifier = Modifier
        .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
        )
        Spacer(modifier = Modifier.height(20.dp))
        // Primeira linha: foto e nome
        Row(
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Image(
                painter = imagePainter,
                contentDescription = "Post Foto",
                modifier = Modifier
                    .size(30.dp)
                    .clip(RoundedCornerShape(30.dp))
            )
            Text(
                text = "@" + post.nome,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp)
            )
        }

        // Segunda linha: mensagem do post
        Text(
            text = post.message,
            color = Color.White,
            modifier = Modifier.padding(start = 8.dp)
        )

        Row(
            modifier = Modifier.padding(start = 8.dp, top = 8.dp)
        )  {
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
fun BottomNavigationBar(navController: NavController) {
    BottomNavigation(
        backgroundColor = Color.Black,
        contentColor = Color.Gray
    ) {
        val items = listOf(
            BottomNavItem("Home", , "home"),
            BottomNavItem("Search", Icons.Default.Search, "search"),
            BottomNavItem("Notifications", Icons.Default.Notifications, "notifications"),
            BottomNavItem("Profile", Icons.Default.Person, "profile")
        )

        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = Color.Gray
                    )
                },
                selected = false, // Pode ser alterado para exibir o item selecionado
                onClick = { navController.navigate(item.route) }
            )
        }
    }
}

data class BottomNavItem(val label: String, val icon: ImageVector, val route: String)


@RequiresApi(Build.VERSION_CODES.O)
//@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewPost() {
    val post1 = Post(
        "rafael.faZion",
        "fazion",
        "${LocalDateTime.now()}",
        "Rpzd o que fazer após ver meu time perder pro yuri alberto?"
    )
    val post2 = Post(
        "gui.OProfeta",
        "profeta",
        "${LocalDateTime.now()}",
        "As extensões de porno do pornhub estão fora de ar há 1 semana, ja estou ficando louco!!!"
    )
    val post3 = Post(
        "rafa.Vasco",
        "vasco",
        "${LocalDateTime.now()}",
        "É só substituir do pornhub pelas do redtube, meu saco ja esta ficando vazio!"
    )
    for (i in 0 until 43) {
        post2.adicionarCurtida()
        post2.adicionarCurtida()
        post1.removerCurtida()
    }
    Column(modifier = Modifier.fillMaxSize()) {
        PostUI(post = post1)
        PostUI(post = post2)
        PostUI(post = post3)
    }
}
