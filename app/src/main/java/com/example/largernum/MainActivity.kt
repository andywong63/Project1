package com.example.largernum

import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.largernum.ui.theme.GeneratedNumber
import kotlin.math.E
import kotlin.math.PI
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    var num1: Double = 0.0
    var num2: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainLayout()
        }
    }

    @Composable
    fun MainLayout() {
        var currentScreen by remember { mutableStateOf("START") }

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

        var lastResult by remember { mutableStateOf("") }
        var gameEnded by remember { mutableStateOf(false) }

        val background = when (lastResult) {
            "CORRECT" -> Color(0xff73ff8a)
            "INCORRECT" -> Color(0xffff7373)
            else -> Color(0xFFFDEF74)
        }

        Background(background) {
            Column(
                modifier = Modifier
                    .padding(top = 64.dp, start = 12.dp, end = 12.dp)
            ) {
                val scoreColor = when (lastResult) {
                    "CORRECT" -> Color(0xffffe047)
                    "WIN" -> Color(0xff30ff91)
                    else -> Color.Black
                }
                Text(
                    text = "Score: $score",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = scoreColor,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.5f),
                            offset = Offset(3f, 3f),
                            blurRadius = 6f
                        )
                    )
                )
                Spacer(Modifier.height(16.dp))
                val strikeColor = when (lastResult) {
                    "INCORRECT" -> Color(0xffffe047)
                    "LOSE" -> Color(0xffff4d4d)
                    else -> Color.Black
                }
                Text(
                    text = "Strikes: $strikes",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = strikeColor,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.5f),
                            offset = Offset(3f, 3f),
                            blurRadius = 6f
                        )
                    )
                )
                Spacer(Modifier.height(32.dp))

                var num1Disp by remember { mutableStateOf("0") }
                var num2Disp by remember { mutableStateOf("0") }
                LaunchedEffect(true) {
                    generateNumbers { n1, n2 ->
                        num1Disp = n1
                        num2Disp = n2
                    }
                }

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(onClick = {
                        lastResult = ""
                        score = 0
                        strikes = 0
                        generateNumbers { n1, n2 ->
                            num1Disp = n1
                            num2Disp = n2
                        }
                        gameEnded = false
                    }) {
                        Text(
                            text = "Restart",
                            fontSize = 24.sp
                        )
                    }
                }
                Spacer(Modifier.height(32.dp))

                if (gameEnded) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Tap restart to play again!",
                            fontSize = 32.sp
                        )
                    }
                } else {
                    val context = LocalContext.current
                    GameColumn(
                        num1Disp,
                        num2Disp,
                        guessSuccess = {
                            score++
                            lastResult = "CORRECT"
                            if (score >= 10) {
                                lastResult = "WIN"
                                gameEnded = true
                                Toast.makeText(context, "Congrats, you won!", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        },
                        guessFail = {
                            strikes++
                            lastResult = "INCORRECT"
                            if (strikes >= 3) {
                                lastResult = "LOSE"
                                gameEnded = true
                                Toast.makeText(context, "Sorry, you lost", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        },
                        generateNumbers = {
                            generateNumbers { n1, n2 ->
                                num1Disp = n1
                                num2Disp = n2
                            }
                        }
                    )
                }
            }
        }
    }

    @Composable
    fun GameColumn(
        num1Disp: String,
        num2Disp: String,
        guessSuccess: () -> Unit,
        guessFail: () -> Unit,
        generateNumbers: () -> Unit
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Tap the larger number!",
                fontSize = 32.sp
            )

            Spacer(Modifier.height(48.dp))
            NumberBox(num1Disp, Color(0xff6bd0ff)) {
                if (num1 >= num2) {
                    guessSuccess()
                } else {
                    guessFail()
                }
                generateNumbers()
            }
            Spacer(Modifier.height(48.dp))
            NumberBox(num2Disp, Color(0xffffbe6e)) {
                if (num2 >= num1) {
                    guessSuccess()
                } else {
                    guessFail()
                }
                generateNumbers()
            }
        }
    }

    fun generateNumbers(updateDispVars: (String, String) -> Unit) {
        val n1 = generateNum()
        val n2 = generateNum()
        num1 = n1.number
        num2 = n2.number

        updateDispVars(n1.display, n2.display)
    }


    @Composable
    @Preview
    fun MainLayoutPreview() {
        MainLayout()
    }
}

fun generateNum(): GeneratedNumber {
    val seed = Random.nextInt(0, 100)

    return when (seed) {
        in 0..9 -> GeneratedNumber(PI, "π")
        in 10..19 -> GeneratedNumber(E, "e")
        else -> {
            val n1 = Random.nextInt(0, 100)
            GeneratedNumber(n1.toDouble(), n1.toString())
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
            .size(150.dp)
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