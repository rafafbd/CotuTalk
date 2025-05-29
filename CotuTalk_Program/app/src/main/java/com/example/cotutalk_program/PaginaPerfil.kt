package com.example.cotutalk_program

import androidx.collection.intIntMapOf
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cotutalk_program.ui.theme.roxo80

@Preview
@Composable
fun paginaPerfil(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(roxo80)
    ) {
        Scaffold (
            modifier = Modifier.background(roxo80),
            bottomBar = {
                BottomNavigationBar(navController)
            }
        )
        {  }

}