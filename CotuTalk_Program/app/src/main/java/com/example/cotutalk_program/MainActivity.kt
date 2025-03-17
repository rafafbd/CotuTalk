package com.example.cotutalk_program

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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

class Post(val nome : String, val foto : String, val dataHorario : String, val message : String){
    var curtidas = 0;
    val comentarios: MutableList<Post> = mutableListOf();

    fun adicionarCurtida() {
        curtidas++
    }

    fun removerCurtida() {
        curtidas--
    }

    fun adicionarComentarios(post : Post) {
        comentarios.add(post);
    }

    fun obterDadosJson() : Map<String, String> {
        var dados = mapOf(
            "nome" to nome,
            "foto" to foto,
            "data" to dataHorario,
            "message" to message,
            "curtidas" to curtidas.toString(),
            "comentarios" to comentarios.joinToString(";")
        )
        return dados
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CotuTalk_ProgramTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
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
    Column(modifier = Modifier
        .padding(8.dp)
        .width(300.dp)
    ) {
        // Primeira linha: foto e nome
        Row(
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
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
        }
    }


    // Exibindo a mensagem do post
//    Row(modifier = Modifier.padding(8.dp)) {
//        Text(text = post.message)
//    }
//
//    // Exibindo curtidas e comentários
//    Row(modifier = Modifier.padding(8.dp)) {
//        Column(modifier = Modifier.weight(1f)) {
//            //Button(onClick = { post.curtidas++ }) {
//                //Text(text = "Curtir")
//            //}
//            Text(text = "Curtidas: ${post.curtidas}")
//        }
//
//        Column(modifier = Modifier.weight(1f)) {
//            //Button(onClick = { /* Adicionar lógica para comentar */ }) {
//                //Text(text = "Comentar")
//            //}
//        }
//    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewPost() {
    val post = Post(
        "rafael.faZion",
        "foto_joao.jpg",
        "${LocalDateTime.now()}",
        "Rpzd o que fazer após ver meu time perder pro yuri alberto?"
    )
    PostUI(post = post)
}