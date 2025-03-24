package com.example.cotutalk_program.ui.theme

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cotutalk_program.Greeting
import com.example.cotutalk_program.ui.theme.CotuTalk_ProgramTheme
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CotuTalk_ProgramTheme {
                CaixaLogin()
            }
        }
    }
}



fun Login(email: String, senha: String){
    // consulta api
    // se o login estiver certo, vai à página principal
    // caso contrário, volta à página de login
}

@Preview
@Composable
fun paginaLogin(){
    Column(
        Modifier
            .background(color = roxo80)
    )
    {

    }
}

@Preview
@Composable
fun CaixaLogin(){
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    Column(
        Modifier.background(color = roxo60),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text("Email:")
        TextField(
            value = email,
            onValueChange = { email = it },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = roxo70,
                unfocusedContainerColor = roxo70
            ),
            modifier = Modifier.fillMaxWidth(0.9f)
        )

        Text("Senha:")
        TextField(
            value = senha,
            onValueChange = { senha = it },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = roxo70,
                unfocusedContainerColor = roxo70
            ),
            modifier = Modifier.fillMaxWidth(0.9f)
        )

        BotaoEstilizado(
            texto = "Entrar",
            click = {Login(email, senha)}
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
        modifier = Modifier.fillMaxWidth(0.9f)
    ){
        Text(texto)
    }
}
