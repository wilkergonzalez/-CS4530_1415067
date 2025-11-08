package com.example.gyroscopedemomvvm

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gyroscopedemomvvm.ui.theme.GyroscopeDemoTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GyroscopeDemoTheme {
                val myVM: GyroscopeViewModel = viewModel(factory = GyroscopeViewModel.Factory)
                GyroscopeScreen(myVM)
            }
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope", "DefaultLocale")
@Composable
fun GyroscopeScreen(viewModel: GyroscopeViewModel) {
    val gyroReading by viewModel.gyroReading.collectAsStateWithLifecycle()

    var marbleX by remember { mutableStateOf(0f) }
    var marbleY by remember { mutableStateOf(0f) }
    var velocityX by remember { mutableStateOf(0f) }
    var velocityY by remember { mutableStateOf(0f) }
    var isInitialized by remember { mutableStateOf(false) }  // Track if we've centered the marble


    val GRAVITY_SCALE = -300f
    val VELOCITY_SCALE = 0.016f
    val FRICTION = 0.95f
    val MAX_VELOCITY = 150f
    val MARBLE_RADIUS = 20f

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            val maxWidth = maxWidth.value
            val maxHeight = maxHeight.value

            LaunchedEffect(maxWidth, maxHeight) {
                if (!isInitialized) {
                  // center
                    marbleX = (maxWidth / 2) - MARBLE_RADIUS
                    marbleY = (maxHeight / 2) - MARBLE_RADIUS
                    isInitialized = true
                }
            }

            LaunchedEffect(gyroReading) {
                val accelX = gyroReading.x * GRAVITY_SCALE
                val accelY = gyroReading.y * GRAVITY_SCALE
                velocityX = (velocityX + accelX * VELOCITY_SCALE) * FRICTION
                velocityY = (velocityY + accelY * VELOCITY_SCALE) * FRICTION
                val speed = kotlin.math.sqrt(velocityX * velocityX + velocityY * velocityY)
                if (speed > MAX_VELOCITY) {
                    val scale = MAX_VELOCITY / speed
                    velocityX *= scale
                    velocityY *= scale
                }
                marbleX += velocityX * VELOCITY_SCALE
                marbleY += velocityY * VELOCITY_SCALE

                if (marbleX < 0) {
                    marbleX = 0f
                    velocityX *= -0.8f
                } else if (marbleX > maxWidth - MARBLE_RADIUS * 2) {
                    marbleX = maxWidth - MARBLE_RADIUS * 2
                    velocityX *= -0.8f
                }

                if (marbleY < 0) {
                    marbleY = 0f
                    velocityY *= -0.8f
                } else if (marbleY > maxHeight - MARBLE_RADIUS * 2) {
                    marbleY = maxHeight - MARBLE_RADIUS * 2
                    velocityY *= -0.8f
                }
                delay(16)
            }
            androidx.compose.foundation.layout.Box(
                modifier = Modifier
                    .offset(x = marbleX.dp, y = marbleY.dp)
                    .size(40.dp)
                    .background(Color.Blue, CircleShape)
            )
            Text(
                text = "Gravity: x=${String.format("%.2f", gyroReading.x)}, y=${String.format("%.2f", gyroReading.y)}\n" +
                        "Marble: x=${String.format("%.1f", marbleX)}, y=${String.format("%.1f", marbleY)}\n" +
                        "Velocity: vx=${String.format("%.1f", velocityX)}, vy=${String.format("%.1f", velocityY)}",
                style = MaterialTheme.typography.bodySmall,
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(10.dp),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}
// gemeni ai helped me with this.
private fun Float.dp() = androidx.compose.ui.unit.Dp(this)