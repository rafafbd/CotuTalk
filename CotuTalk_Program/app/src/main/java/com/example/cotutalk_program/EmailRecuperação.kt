package com.example.cotutalk_program

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cotutalk_program.ui.theme.BotaoEstilizado
import com.example.cotutalk_program.ui.theme.branco
import com.example.cotutalk_program.ui.theme.roxo60
import com.example.cotutalk_program.ui.theme.roxo70
import com.example.cotutalk_program.ui.theme.roxo80


@Preview
@Composable
fun EmailRecuperacao(navController: Any) {

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
        CaixaLogin3()
    }
}


@Composable
fun CaixaLogin3(){
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
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
            Box (modifier = Modifier.fillMaxWidth(0.9f)) {
                Text(
                    "Enviamos um código de verificação para o email u24159@g.unicamp.br, insira o código no campo a baixo para prosseguir com a recuperação de senha",
                    color = branco,
                    fontSize = 19.sp
                )
                Spacer(Modifier.height(180.dp))
            }
//            Spacer(Modifier.height(50.dp))
            Box (modifier = Modifier.fillMaxWidth(0.9f)) {
                Text(
                    "Código de verificação:",
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

            Spacer(Modifier.height(180.dp))

            Column (
                Modifier.fillMaxWidth(0.9f),
                horizontalAlignment = Alignment.Start
            ) {
                BotaoEstilizado(
                    texto = "Verificar",
                    click = { Login(email, senha) }
                )
            }

        }
    }

}