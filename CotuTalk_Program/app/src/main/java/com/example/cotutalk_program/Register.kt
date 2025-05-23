package com.example.cotutalk_program

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cotutalk_program.AcessoAPI.viewmodel.UsuarioViewModel
import com.example.cotutalk_program.ui.theme.BotaoEstilizado
import com.example.cotutalk_program.ui.theme.branco
import com.example.cotutalk_program.ui.theme.roxo60
import com.example.cotutalk_program.ui.theme.roxo70
import com.example.cotutalk_program.ui.theme.roxo80
import androidx.compose.ui.platform.LocalContext
import com.example.cotutalk_program.InfoUsuario
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay


@Composable
fun paginaRegistrar(navController: NavController) {

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
            modifier = Modifier.size(200.dp)
        )
        CaixaRegistrar(navController)
    }
}


@Composable
fun CaixaRegistrar(navController: NavController){
    val viewmodel = UsuarioViewModel()
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var confiSenha by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var infoValidada by remember { mutableStateOf(false) }
    Box (
        Modifier
            .fillMaxSize(0.85f)
            .clip(RoundedCornerShape(16.dp))
    ){
        Column(
            Modifier
                .background(color = roxo60)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

            ){
            Spacer(Modifier.height(50.dp))

            Box (modifier = Modifier.fillMaxWidth(0.9f),){
                Text("Email:",
                    color = branco,
                    modifier = Modifier.padding(start = 8.dp, bottom = 5.dp),
                    fontSize = 22.sp
                )
            }

            TextField(
                value = email,
                onValueChange = { email = it },
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


            Spacer(Modifier.height(22.dp))

            Box (modifier = Modifier.fillMaxWidth(0.9f),){
                Text("Senha:",
                    color = branco,
                    modifier = Modifier.padding(start = 8.dp, bottom = 5.dp),
                    fontSize = 22.sp
                )
            }

            TextField(
                value = senha,
                onValueChange = { senha = it },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = roxo70,
                    unfocusedContainerColor = roxo70,
                    focusedTextColor = branco,
                    unfocusedTextColor = branco
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        BorderStroke(0.dp, Color.Black), // Border color and thickness
                        shape = RectangleShape
                    )
                    .shadow(4.dp, RectangleShape)
                    .fillMaxWidth(0.9f)
            )

            Spacer(Modifier.height(22.dp))

            Box (modifier = Modifier.fillMaxWidth(0.9f),){
                Text("Confirmar senha:",
                    color = branco,
                    modifier = Modifier.padding(start = 8.dp, bottom = 5.dp),
                    fontSize = 22.sp
                )
            }

            TextField(
                value = confiSenha,
                onValueChange = { confiSenha = it },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = roxo70,
                    unfocusedContainerColor = roxo70,
                    focusedTextColor = branco,
                    unfocusedTextColor = branco
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .border(
                        BorderStroke(0.dp, Color.Black), // Border color and thickness
                        shape = RectangleShape
                    )
                    .shadow(4.dp, RectangleShape)
                    .fillMaxWidth(0.9f)
            )

            Box (modifier = Modifier.fillMaxWidth(0.9f),){
                Text(message,
                    color = Color.Red,
                    modifier = Modifier.padding(start = 8.dp, bottom = 5.dp),
                    fontSize = 20.sp
                )
            }

            Spacer(Modifier.height(140.dp))

            Column (
                Modifier.fillMaxWidth(0.9f),
                horizontalAlignment = Alignment.Start
            ) {
                BotaoEstilizado(
                    texto = "Registrar",
                    click = {
                        if (senha == confiSenha) {
                            infoValidada = true
                        } else {
                            message = "*Senhas diferentes"
                        }
                    }
                )
                Text(
                    text = "Login",
                    color = branco,
                    textDecoration = TextDecoration.Underline,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .clickable { levaAoSignUp() }
                        .padding(start = 10.dp)
                )
            }

            if (infoValidada) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xAA000000)), // fundo escuro semi-transparente
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = "https://media.giphy.com/media/v1.Y2lkPTc5MGI3NjExZmRpbnNhbm94Z3hrdXl6b284dmRhcWs2bDVpamN5aHA5OXEzZzRwYSZlcD12MV9naWZzX3NlYXJjaCZjdD1n/wvtt4mtViPOSrLYNFh/giphy.gif",
                        contentDescription = "Carregando...",
                        modifier = Modifier.size(150.dp)
                    )
                }
                val context = LocalContext.current
                LaunchedEffect(infoValidada) {
                    delay(2000)

                    val usuario = InfoUsuario(
                        Nome = "",
                        Biografia = "",
                        Email = email,
                        Senha = senha
                    )
                    val sharedPref = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putString("Nome", usuario.Nome)
                        putString("Biografia", usuario.Biografia)
                        putString("Email", usuario.Email)
                        putString("Senha", usuario.Senha)
                        apply()
                    }
                    viewmodel.enviarEmail(email, navController)
                }
            }

        }
    }

}