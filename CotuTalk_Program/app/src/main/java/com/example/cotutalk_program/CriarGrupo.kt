package com.example.cotutalk_program.ui.theme

import android.app.Activity
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cotutalk_program.BottomNavigationBar
import com.example.cotutalk_program.Post
import com.example.cotutalk_program.PostUI
import com.example.cotutalk_program.R
import com.example.cotutalk_program.ui.theme.CotuTalk_ProgramTheme
import java.time.LocalDateTime

class CriarGrupo : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CotuTalk_ProgramTheme {

            }
        }
    }
}

@Preview
@Composable
fun PaginaCriarGrupo(){
    var nomeGrupo by remember { mutableStateOf("") }
    var descGrupo by remember { mutableStateOf("") }
    var ehPrivado by remember { mutableStateOf(false) }
    Scaffold (
        bottomBar = { BottomNavigationBar(
            onHomeClick = { },
            onSearchClick = { },
            onNotificationsClick = { },
            onProfileClick = { }
        ) }
    ) { innerPadding ->
        Column(
            Modifier
                .background(roxo80)
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo do app",
                modifier = Modifier.size(200.dp)
            )

            Text("Nome do grupo:",
                color = branco,
                fontSize = 16.sp)

            TextField(
                value = nomeGrupo,
                onValueChange = { nomeGrupo = it },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = roxo70,
                    unfocusedContainerColor = roxo70,
                    focusedTextColor = branco,
                    unfocusedTextColor = branco
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth(0.9f)
            )

            Spacer(Modifier.height(25.dp))

            Text("Descrição do grupo:",
                color = branco,
                fontSize = 16.sp
                )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 10.dp)) {

                TextField(
                    value = descGrupo,
                    onValueChange = { descGrupo = it },
                    singleLine = false,
                    minLines = 15,
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


                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = ehPrivado,
                        onCheckedChange = { ehPrivado = it }
                    )
                    Text("Privado",
                        color = branco,
                        fontSize = 16.sp)
                }


                Spacer(Modifier.height(25.dp))

                Box (Modifier.fillMaxWidth(0.9f)){
                    BotaoEstilizado("Criar") { }
                }
            }
        }
    }
}