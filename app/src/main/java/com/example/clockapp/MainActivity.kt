package com.example.clockapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import java.util.Calendar
import kotlin.math.cos
import kotlin.math.sin
import com.example.clockapp.ui.theme.ClockAppTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClockAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ClockScreen()
                }
            }
        }
    }
}

@Composable
fun ClockScreen() {
    var selectedItem by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.Black,
                contentColor = Color.White
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = selectedItem == 0,
                    onClick = { selectedItem = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Info, contentDescription = "About") },
                    label = { Text("About") },
                    selected = selectedItem == 1,
                    onClick = { selectedItem = 1 }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(innerPadding)
        ) {
            when (selectedItem) {
                0 -> HomeContent()
                1 -> AboutContent()
            }
        }
    }
}

@Composable
fun HomeContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "REET KUMAR BIND",
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 48.dp, bottom = 0.dp)
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            AnalogClock()
        }
    }
}

@Composable
fun AboutContent() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Clock App\nCreated by Reet Kumar Bind\nVersion 2.0",
            color = Color.White,
            fontSize = 20.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun AnalogClock() {
    var currentTime by remember { mutableStateOf(Calendar.getInstance()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = Calendar.getInstance()
            delay(1000L)
        }
    }

    Canvas(
        modifier = Modifier
            .size(350.dp)
            .padding(16.dp)
    ) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val radius = size.minDimension / 2

        drawCircle(
            color = Color.White,
            radius = radius,
            center = Offset(centerX, centerY)
        )

        for (i in 0 until 12) {
            val angle = Math.PI / 6 * i - Math.PI / 2
            val markerStart = Offset(
                x = centerX + (radius * 0.9f * cos(angle)).toFloat(),
                y = centerY + (radius * 0.9f * sin(angle)).toFloat()
            )
            val markerEnd = Offset(
                x = centerX + (radius * cos(angle)).toFloat(),
                y = centerY + (radius * sin(angle)).toFloat()
            )
            drawLine(
                color = Color.Black,
                start = markerStart,
                end = markerEnd,
                strokeWidth = 5f
            )
        }

        val hours = currentTime.get(Calendar.HOUR)
        val minutes = currentTime.get(Calendar.MINUTE)
        val seconds = currentTime.get(Calendar.SECOND)

        val secondAngle = Math.PI / 30 * seconds - Math.PI / 2
        val minuteAngle = Math.PI / 30 * minutes + Math.PI / 1800 * seconds - Math.PI / 2
        val hourAngle = Math.PI / 6 * hours + Math.PI / 360 * minutes - Math.PI / 2

        drawLine(
            color = Color.Black,
            start = Offset(centerX, centerY),
            end = Offset(
                centerX + (radius * 0.5f * cos(hourAngle)).toFloat(),
                centerY + (radius * 0.5f * sin(hourAngle)).toFloat()
            ),
            strokeWidth = 12f,
            cap = StrokeCap.Round
        )

        drawLine(
            color = Color.Black,
            start = Offset(centerX, centerY),
            end = Offset(
                centerX + (radius * 0.7f * cos(minuteAngle)).toFloat(),
                centerY + (radius * 0.7f * sin(minuteAngle)).toFloat()
            ),
            strokeWidth = 8f,
            cap = StrokeCap.Round
        )

        drawLine(
            color = Color.Red,
            start = Offset(centerX, centerY),
            end = Offset(
                centerX + (radius * 0.9f * cos(secondAngle)).toFloat(),
                centerY + (radius * 0.9f * sin(secondAngle)).toFloat()
            ),
            strokeWidth = 4f,
            cap = StrokeCap.Round
        )

        drawCircle(
            color = Color.Black,
            radius = 8f,
            center = Offset(centerX, centerY)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ClockScreenPreview() {
    ClockAppTheme {
        ClockScreen()
    }
}