package com.example.crypto.screens.first

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.crypto.R
import com.example.crypto.navigation.Screens


@Composable
fun FirstScreen(navController: NavController) {
    PresentInformation(navController = navController)
}

@Composable
fun PresentInformation(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        GifImage()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WelcomeText("Welcome to Crypto")
            Spacer(modifier = Modifier.height(16.dp))
            WelcomeText("Let's start our journey together!")
            Spacer(modifier = Modifier.weight(1f))
            NavigateButton(navController)
        }
    }
}

@Composable
fun WelcomeText(text: String) {
    Text(
        text = text,
        color = Color.White,
        fontSize = 24.sp
    )
}

@Composable
fun NavigateButton(navController: NavController) {
    Button(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        onClick = {
            navController.navigate(Screens.Home.route)
        },
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White
        )
    ) {
        Text(
            text = "Let's check some cryptocurrency",
            modifier = Modifier.padding(5.dp),
        )
    }
}

@Composable
fun GifImage() {
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    Image(
        painter = rememberAsyncImagePainter(R.drawable.cryptocoin, imageLoader),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}

@Composable
@Preview
fun FirstScreenPreview() {
    val navController = rememberNavController()
    FirstScreen(navController)
}