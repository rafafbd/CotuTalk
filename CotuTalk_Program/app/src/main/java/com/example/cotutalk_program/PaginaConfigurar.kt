package com.example.cotutalk_program

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
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


@Preview
@Composable
fun paginaConfigurar(navController: NavController) {

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
        CaixaConfigurar(navController)
    }
}


@Composable
fun CaixaConfigurar(navController : NavController){
    val viewmodel = UsuarioViewModel()
    var nome by remember { mutableStateOf("") }
    var biografia by remember { mutableStateOf("") }
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
//            Spacer(Modifier.height(50.dp))
            Box (modifier = Modifier.fillMaxWidth(1f)) {
                Text(
                    "Bem-vindo ao cotutalk!",
                    color = branco,
                    modifier = Modifier.padding(start = 8.dp, bottom = 50.dp),
                    fontSize = 30.sp
                )
            }



            Image(
                painter = painterResource(id = R.drawable.icon_camera),
                contentDescription = "foto do perfil",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(50.dp)) // Aplica bordas arredondadas
            )


            Spacer(Modifier.height(22.dp))

            Box (modifier = Modifier.fillMaxWidth(0.9f),){
                Text("Nome:",
                    color = branco,
                    modifier = Modifier.padding(start = 8.dp, bottom = 5.dp),
                    fontSize = 22.sp
                )
            }

            TextField(
                value = nome,
                onValueChange = { nome = it },
                singleLine = true,
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
                Text("Biografia:",
                    color = branco,
                    modifier = Modifier.padding(start = 8.dp, bottom = 5.dp),
                    fontSize = 22.sp
                )
            }

            TextField(
                value = biografia,
                onValueChange = { biografia = it },
                singleLine = true,
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


            Spacer(Modifier.height(50.dp))

            Column (
                Modifier.fillMaxWidth(0.9f),
                horizontalAlignment = Alignment.Start
            ) {
                val context = LocalContext.current
                BotaoEstilizado(
                    texto = "Criar conta",
                    click = {
                        val sharedPref = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                        with(sharedPref.edit()) {
                            putString("Nome", nome)
                            putString("Biografia", biografia)
                            apply()
                        }
                        val email = sharedPref.getString("Email", "") ?: ""
                        val senha = sharedPref.getString("Senha", "") ?: ""

                        viewmodel.adicionarUsuario(nome, email, senha, biografia, navController)
                    }
                )

            }

        }
    }
}