package com.example.cotutalk_program

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.cotutalk_program.AcessoAPI.data.Postagem
import com.example.cotutalk_program.AcessoAPI.data.PostagemUI
import com.example.cotutalk_program.AcessoAPI.viewmodel.PostagemViewModel
import com.example.cotutalk_program.ui.theme.Post
import com.example.cotutalk_program.ui.theme.roxo80


@Composable
fun TelaRespostas(navController: NavController, postModel: PostagemViewModel, idPost: Int){

    Scaffold(
        modifier = Modifier.background(roxo80),
        bottomBar = {

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(roxo80)
                .padding(innerPadding)
        ) {
            //Post(oPost)

            // lógica para todas as postagens
        }
    }
}
