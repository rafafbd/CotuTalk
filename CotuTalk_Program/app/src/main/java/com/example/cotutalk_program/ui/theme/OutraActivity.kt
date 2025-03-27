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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cotutalk_program.Greeting
import com.example.cotutalk_program.R
import com.example.cotutalk_program.ui.theme.CotuTalk_ProgramTheme
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CotuTalk_ProgramTheme {
                paginaLogin()
            }
        }
    }
}


fun levaAoSignUp(){
    // função que não faz nada e vai ser apagada posteriormente
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
        modifier = Modifier
            .background(color = roxo80)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo do app",
            modifier = Modifier.size(150.dp)
        )
        CaixaLogin()
    }
}


@Composable
fun CaixaLogin(){
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    Box (
        Modifier.fillMaxSize(0.85f)
            .clip(RoundedCornerShape(16.dp))
    ){
        Column(
            Modifier
                .background(color = roxo60)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

            ){
//            Spacer(Modifier.height(50.dp))
            Column (modifier = Modifier.fillMaxWidth(0.9f)) {
                Text("Email:",
                    color = branco,
                    modifier = Modifier.padding(start = 8.dp, bottom = 5.dp),
                    fontSize = 15.dp
                )
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = roxo70,
                        unfocusedContainerColor = roxo70
                    ),
                    modifier = Modifier.clip(RoundedCornerShape(10.dp))
                )
            }

            Spacer(Modifier.height(15.dp))

            Column (modifier = Modifier.fillMaxWidth(0.9f)
                .align(Alignment.CenterHorizontally)
            ) {
                Text("Senha:",
                    color = branco,
                    modifier = Modifier.padding(start = 8.dp, bottom = 5.dp)
                )
                TextField(
                    value = senha,
                    onValueChange = { senha = it },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = roxo70,
                        unfocusedContainerColor = roxo70
                    ),
                    modifier = Modifier.clip(RoundedCornerShape(10.dp))
                )
            }

            Column (Modifier.fillMaxWidth(0.5f),
                horizontalAlignment = Alignment.Start
            ) {
                BotaoEstilizado(
                    texto = "Entrar",
                    click = {Login(email, senha)}
                )
                Text(
                    text = "Criar conta",
                    color = branco,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { levaAoSignUp() }
                )
            }

        }
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
        modifier = Modifier.fillMaxWidth()
    ){
        Text(texto)
    }
}
