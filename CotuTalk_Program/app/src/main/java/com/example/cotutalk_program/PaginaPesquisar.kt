package com.example.cotutalk_program

import android.os.Build
import android.os.Bundle
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cotutalk_program.ui.theme.CotuTalk_ProgramTheme
import java.time.LocalDateTime
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.example.cotutalk_program.ui.theme.BotaoEstilizado
import com.example.cotutalk_program.ui.theme.Login
import com.example.cotutalk_program.ui.theme.branco
import com.example.cotutalk_program.ui.theme.levaAoSignUp
import com.example.cotutalk_program.ui.theme.roxo60
import com.example.cotutalk_program.ui.theme.roxo70
import com.example.cotutalk_program.ui.theme.roxo80


@Preview
@Composable
fun Search(){
    var pesquisa by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(roxo80),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        TextField(

            value = pesquisa,
            onValueChange = {pesquisa = it},
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = roxo70,
                unfocusedContainerColor = roxo70,
                focusedTextColor = branco,
                unfocusedTextColor = branco
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.icon_search),
                    contentDescription = "",
                    tint = Color.White
                )
            },
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .border(
                    BorderStroke(0.dp, Color.Black),
                    shape = RectangleShape
                )
                .shadow(4.dp, RectangleShape)
                .fillMaxWidth(0.9f)
                .padding(top = 20.dp)
                .height(50.dp)

        )

        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.alpha(0.5f).
            fillMaxSize(0.7f)
        )

        BottomNavigationBar(
            onHomeClick = { },
            onSearchClick = { },
            onNotificationsClick = { },
            onProfileClick = { }
        )
    }
}

