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
import com.example.cotutalk_program.Post
import com.example.cotutalk_program.PostUI
import com.example.cotutalk_program.R
import com.example.cotutalk_program.ui.theme.CotuTalk_ProgramTheme
import java.time.LocalDateTime

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

@Preview
@Composable
fun responder(){
    var resposta by remember { mutableStateOf("") }
    Column(Modifier
        .background(roxo80)
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PostUI(
            Post("Vini.guep",
                "defaultprofile",
                "",
                "Como eu faço herança em C#???")
        )
        Column(modifier = Modifier.padding(bottom = 10.dp).
        drawBehind {
            drawLine(
                color = branco, // Border color
                start = Offset(0f, 0f), // Start from the top-left
                end = Offset(size.width, 0f), // End at the top-right
                strokeWidth = 1.dp.toPx() // Thickness of the border
            )

        }){

            TextField(
                value = resposta,
                onValueChange = { resposta = it },
                singleLine = false,
                minLines = 10,
                placeholder = {
                    Text("Escreva sua resposta aqui...", color = branco)},
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

            BotaoEstilizado("Responder") { postarResposta(resposta) }
        }
    }

}