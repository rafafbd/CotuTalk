package com.example.cotutalk_program

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cotutalk_program.ui.theme.lilas10
import com.example.cotutalk_program.ui.theme.lilas20
import com.example.cotutalk_program.ui.theme.roxo80

@Preview(showBackground = true)
@Composable
fun PreviewPaginaGrupo() {
    val navController = rememberNavController()
    PaginaGrupo(navController = navController)
}

@Composable
fun PaginaGrupo(navController: NavController, /*idGrupo: Int*/){
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.background(roxo80),
        bottomBar = {
            BottomNavigationBar(navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("paginaPostar")
                },
                containerColor = lilas20,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Text(
                    text = "+",
                    fontSize = 30.sp,
                    color = Color.White
                )
            }
        },
        floatingActionButtonPosition = androidx.compose.material3.FabPosition.End,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(roxo80)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(all = 5.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.vasco),
                    contentDescription = "Imagem placeholder",
                    modifier = Modifier
                        .clip(CircleShape)
                        .fillMaxWidth(0.3f)
                )
                Text(
                    text = "Nome do grupo",
                    color = Color.White,
                    fontSize = 30.sp,
                    modifier = Modifier.fillMaxWidth(0.7f).padding(horizontal = 10.dp)
                )
            }
            Text(
                text = "Descrição do grupo lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum lorem ipsum",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.padding(vertical = 20.dp)
                )

        }

    }
}