package com.example.largernum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.largernum.ui.theme.LargerNumberMathGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainLayout()
        }
    }
}

@Composable
fun MainLayout() {
    var currentScreen by remember { mutableStateOf("START") }

    if (currentScreen == "START") {
        StartScreen {
            currentScreen = "GAME"
        }
    }
}

@Composable
fun StartScreen(onStartClick: () -> Unit) {
    Background(Color(0xFFFDEF74)) {
        Button(onClick = onStartClick) {
            Text(
                text = "Start",
                fontSize = 24.sp
            )
        }
        Spacer(Modifier.height(32.dp))
        Text(
            text = "Tap Start to begin",
            fontSize = 32.sp
        )
        Spacer(Modifier.height(32.dp))
        Image(
            painter = painterResource(R.drawable.launchimage),
            contentDescription = "Find the bigger number",
            modifier = Modifier
                .width(300.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(4.dp, Color.Red, RoundedCornerShape(16.dp))
        )
    }
}


@Composable
fun Background(
    color: Color,
    modifier: Modifier = Modifier,
    content: @Composable (ColumnScope.() -> Unit)
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .background(color),
        content = content
    )
}


@Composable
@Preview
fun MainLayoutPreview() {
    MainLayout()
}