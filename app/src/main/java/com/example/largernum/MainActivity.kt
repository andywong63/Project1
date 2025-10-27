package com.example.largernum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random
import kotlin.random.nextInt

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
//    var currentScreen by remember { mutableStateOf("START") }
    var currentScreen by remember { mutableStateOf("GAME") }

    when (currentScreen) {
        "START" -> StartScreen {
            currentScreen = "GAME"
        }
        "GAME" -> GameScreen()
    }
}

@Composable
fun StartScreen(onStartClick: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color(0xFFFDEF74))
            .fillMaxSize()
    ) {
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
        Spacer(Modifier.height(164.dp))
    }
}

@Composable
fun GameScreen() {
    var score by remember { mutableIntStateOf(0) }
    var strikes by remember { mutableIntStateOf(0) }

    var num1 by remember { mutableIntStateOf(0) }
    var num2 by remember { mutableIntStateOf(0) }
    var num1Disp by remember { mutableStateOf("0") }
    var num2Disp by remember { mutableStateOf("0") }

    var lastWon by remember { mutableStateOf(false) }
    var lastFail by remember { mutableStateOf(false) }

    Background(Color(0xFFFDEF74)) {
        Column(
            modifier = Modifier
                .padding(top = 64.dp, start = 12.dp, end = 12.dp)
        ) {
            Text(
                text = "Score: $score",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Strikes: $strikes",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(32.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = {}) {
                    Text(
                        text = "Restart",
                        fontSize = 24.sp
                    )
                }
                Spacer(Modifier.height(32.dp))
                Text(
                    text = "Tap the larger number!",
                    fontSize = 32.sp
                )
                Spacer(Modifier.height(48.dp))
                NumberBox(num1Disp, Color(0xff6bd0ff)) {
                    if (num1 >= num2) {
                        score++
                    } else {
                        strikes++
                    }
                    num1 = Random.nextInt(0..100)
                    num2 = Random.nextInt(0..100)
                    num1Disp = num1.toString()
                    num2Disp = num2.toString()
                }
                Spacer(Modifier.height(48.dp))
                NumberBox(num2Disp, Color(0xffffbe6e)) {
                    if (num2 >= num1) {
                        score++
                    } else {
                        strikes++
                    }
                    num1 = Random.nextInt(0..100)
                    num2 = Random.nextInt(0..100)
                    num1Disp = num1.toString()
                    num2Disp = num2.toString()
                }
            }
        }
    }
}

@Composable
fun NumberBox(
    number: String,
    color: Color,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(128.dp)
            .background(color)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = number,
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
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