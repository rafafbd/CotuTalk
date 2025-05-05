package com.example.cotutalk_program.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cotutalk_program.BottomNavigationBar
import com.example.cotutalk_program.Post
import com.example.cotutalk_program.PostUI

class PaginaResponder : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CotuTalk_ProgramTheme {

            }
        }
    }
}

fun postarResposta(resposta: String){
    //
    //
    //
}


@Composable
fun responder(navController: NavHostController) {
    var resposta by remember { mutableStateOf("") }
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
            PostUI(
                Post(
                    "Vini.guep",
                    "defaultprofile",
                    "",
                    "Como eu faço herança em C#???"
                )
            )

            Spacer(Modifier.height(25.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 10.dp)) {

                TextField(
                    value = resposta,
                    onValueChange = { resposta = it },
                    singleLine = false,
                    minLines = 20,
                    placeholder = {
                        Text("Escreva sua resposta aqui...", color = branco)
                    },
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

                Box (Modifier.fillMaxWidth(0.9f)){
                    BotaoEstilizado("Responder") { postarResposta(resposta) }
                }
            }
        }
    }
}